package com.tools;

import java.util.List;
import java.util.Random;


public class DataManagement {

	private DataSet myDataSet;// 数据集

	public DataSet getMyDataSet() {
		return myDataSet;
	}

	public void setMyDataSet(DataSet myDataSet) {
		this.myDataSet = myDataSet;
	}

	// 品种数据(RiceGrow和ORYZA2000共用)
	private String riceName;// 水稻品种
	private String plantStyle;// 种植方式
	private String plantPlace;// 种植地点
	private double plantDimension;// 种植维度 这里是输入的纬度还是维度啊？？？
	private double plantDepth;// 种植深度
	private int Tempguanli;// 温度管理方案
	
	public String getRiceName() {
		return riceName;
	}

	public void setRiceName(String riceName) {
		this.riceName = riceName;
	}

	public String getPlantStyle() {
		return plantStyle;
	}

	public void setPlantStyle(String plantStyle) {
		this.plantStyle = plantStyle;
	}

	public String getPlantPlace() {
		return plantPlace;
	}

	public void setPlantPlace(String plantPlace) {
		this.plantPlace = plantPlace;
	}

	public double getPlantDimension() {
		return plantDimension;
	}

	public void setPlantDimension(double plantDimension) {
		this.plantDimension = plantDimension;
	}

	public double getPlantDepth() {
		return plantDepth;
	}

	public void setPlantDepth(double plantDepth) {
		this.plantDepth = plantDepth;
	}

	public int getTempguanli() {
		return Tempguanli;
	}

	public void setTempguanli(int tempguanli) {
		Tempguanli = tempguanli;
	}

	

	public double[][] SimuDataSet = new double[200][10];

	public String strWeatherFile;// 气象文件 单点
	public String strPHEFile;// 物候期文件 单点
	public String strWeatherFile_dp;// 气象文件 多点
	public String strPHEFile_dp;// 物候期文件 多点
	public String strInfoFile;// ？？？这里应该是输入的值吧

	public List<String> strPlace;// //存放已加载气象多点数据地点
	public int internodeNumber;// 主茎节间数
	public double lowestTemp;// 最低温度

	public String[] strPHE = { "出苗期", "分蘖期", "拔节期", "孕穗期", "抽穗期", "开花期", "灌浆期",
			"成熟期" };
	public String[] strPHEO = { "光敏期", "孕穗期", "抽穗期", "成熟期" };

	// 模型数据
	private double[][] paraRange;// RiceGrow参数范围
	private int[] PHE;// RiceGrow调参物候

	private double[][] paraRangeO;// ORYZA2000参数范围
	private int[] PHEO;// ORYZA2000调参物候
	private int ismodel;// n:1为RiceGrow_POT的预测，2为RiceGrow_HDD的预测,3为ORYZA_POT的预测
	private boolean isYSFH; // 是否启用幼穗分化
	
	public double[][] getParaRange() {
		return paraRange;
	}

	public void setParaRange(double[][] paraRange) {
		this.paraRange = paraRange;
	}

	public int[] getPHE() {
		return PHE;
	}

	public void setPHE(int[] pHE) {
		PHE = pHE;
	}

	public double[][] getParaRangeO() {
		return paraRangeO;
	}

	public void setParaRangeO(double[][] paraRangeO) {
		this.paraRangeO = paraRangeO;
	}

	public int getIsmodel() {
		return ismodel;
	}

	public void setIsmodel(int ismodel) {
		this.ismodel = ismodel;
	}

	public boolean isYSFH() {
		return isYSFH;
	}

	public void setYSFH(boolean isYSFH) {
		this.isYSFH = isYSFH;
	}

	

	// 算法数据
	public double[] sg_SPHE;// 调参物候期
	public double[] sg_wPHE;// 物候期权重

	public int sg_TM;
	public int sg_ParaNum;
	public int sg_BvalNum;
	public int sg_LoopTime;// 重复实验次数
	public double sg_Pc;
	public double sg_Pa;
	public double sg_Pm;
	public double[][] sg_dRange;// 参数范围
	public int sg_internodeNumber;// 节间
	public int sg_DNumber;// 调参年份
	public List<String> Dplace;// 调参地点
	public double sg_Pmw;
	public double sg_Pmb;
	public double sg_Pcb;
	public double sg_Pcw;
	public int sg_M;
	public double sg_Pcu;

	// 其他输入数据
	public List<String[]> sObjInManagment;
	public List<Integer> RandList;

	// 气象数据
	public List<String[][]> MetoManagment;
	public String[][] Meto;
	public String[] ObjIn;

	public int[] getPHEO() {
		return PHEO;
	}

	public void setPHEO(int[] pHEO) {
		PHEO = pHEO;
	}

	// 物候期实测值
	public List<Double[]> PHEManagment;
	public int isShockFlag = 0;
	public int[][] ODPHE;// 物候期观测真实数据
	public double[][] TMAX;// 声明矩阵TMAX，存储气象数据中的日最高温(单点) 单点是什么意思？
	public double[][] TMIN;// 声明矩阵TMIN，存储气象数据中的日最低温(单点)

	public DataTable dt = new DataTable("PHEDate");
	public DataTable dw = new DataTable("Weather");

	public double[][] arrayFitness;

	public DataManagement() {
		myDataSet = new DataSet();
		paraRange = new double[5][2];
		paraRangeO = new double[5][2];
		PHE = new int[8];
		PHEO = new int[4];
		
		isYSFH = false;
		//不启用幼穗分化   这里是手工启用？？？而不是根据茎节间数来判断？？？
		if(isYSFH == true){
			strPHE[2]="穗分化";
		}else{
			strPHE[2]="拔节期";
		}		
	}
	
	/**
	 * IAGA界面
	 */
	//加载数据
	public boolean loadDataIAGA(double [] m_paraIAGA,int m_ismodel){
		ismodel = m_ismodel;
		if(m_paraIAGA.length == 5){
			return true;
		}else{
			return false;
		}
	}
	

}
