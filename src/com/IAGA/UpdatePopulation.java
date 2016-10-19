package com.IAGA;

/**
 * 
 * @author zhaokongnuan IAGA算法种群更新代码 版本：PUpdate-IAGA_1.0.0
 * 输入：气象、栽培部分参数个数固定；品种参数个数不定 
 * 输出：对于影响本模型的计算参数，包括累计量与非累计量，不通过接口传递
 *
 */
public class UpdatePopulation {
	 //算法对象
	PUpdate_IAGA mIAGA = new PUpdate_IAGA();
	 //算法参数
	protected double pc = 0; //交叉概率
	protected double pm = 0; //变异概率
	protected double PA = 0; //分割因子pa值最大值
	protected double TM = 0; //最大迭代次数

	/**
	 * 组件描述
	 */
	 //组件描述，用属性
	String _Name = "PUpdate-IAGA_1.0.0"; //名称
	String _Time = "20121004"; //发布时间
	String _Ver = "Ver 1.0.0"; //版本号
	String _OutErr = ""; //错误信息输出

	public String get_Name() {
		return _Name;
	}

	public String get_Time() {
		return _Time;
	}

	public String get_Ver() {
		return _Ver;
	}

	public String get_OutErr() {
		return _OutErr;
	}

	 //初始化不变的算法参数
	public boolean IniAlgoPara(double[] Para) {
		if (Para.length == 4) {
			pc = Para[0];
			pm = Para[1];
			PA = Para[2];
			TM = Para[3];
			return true;
		} else {
			_OutErr = "算法参数个数不对！";
			return false;
		}

	}

	/**
	 * IAGA种群更新对外函数
	 * 
	 * @param current
	 *            目前种群迭代代数
	 * @param UmaxUmin
	 *            [][] 参数范围
	 * @param beatv
	 *            [] 种群最优个体
	 * @param bval
	 *            [][] 种群信息
	 * @param fit
	 *            []
	 * @return bval[][]：更新后的种群信息
	 * @return boolean
	 */
	public boolean UpdatePopu(double current, double[][] UmaxUmin,
			double[] bestv, Double[][] bval, double[] fit) {
		int N = bval.length; //获取行数 种群大小
		int PN = bval[0].length; //获取列数 参数个数
		double pa = 0; //分割因子
		Double[][] newbval = new Double[N][PN]; //新种群
		Double[][] PRange = new Double[2][PN]; //新的参数范围

		pa = mIAGA.ComputPa(current, TM, PA); //计算分割因子pa
		int number = (int) (N - N * pa); //记录选择个体数目
		mIAGA.SelectionFit(number, bval, fit, newbval); //选择算子(轮盘赌方法)
		mIAGA.ComputPR(newbval, number, PRange); //更新种群个体基因值范围
		mIAGA.CrossoverSBX(number, pc, newbval); //模拟二进制交叉算子(SBX)
		mIAGA.MutationNonUPSO(number, pm, current, TM, bestv, UmaxUmin, newbval); //半粒子群变异算子(SPSMO)
		mIAGA.IndiAdvantage(number, bestv, PRange, newbval, UmaxUmin); //个体优势算子(IAO)
		// 更新后种群覆盖原种群bval，返回bval
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < PN; j++) {
				bval[i][j] = newbval[i][j];
			}
		}
		return true;
	}

}
