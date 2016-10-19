package com.IAGA;

public class PUpdate_base {
	
	/**
	 * 计算分割因子Pa值
	 * @param current
	 * @param TM
	 * @param PM
	 * @return
	 */
	public double ComputPa(double current,double TM,double PM){
		return 0;
	}
	
	//选择算子（轮盘赌法）
	public boolean SelectionFit(int number,Double [][] bval,double [] fit,Double[][] newbval){
		return true;
	}
	
	//更新种群个体基因值范围
	public boolean ComputPR(Double [][] bval,int number,Double[][] PR){
		return true;
	}
	
	//模拟二进制交叉算子（SBX）
	public boolean CrossoverSBX(int number,double pc,Double[][] newbval){
		return true;
	}
	
	//半粒子群变异算子(SPSMO)
	public boolean MutationNonUPSO(int number,double pm,double current,double TM,double []bestv,double [][]UmaxUmin,Double[][] newbval){
		return true;
	}
	
	//个体优势算子（IAO）
	public boolean IndiAdvantage(int number,double []bestv,Double[][] PRange,Double[][] newbval,double [][] UmaxUmin){
		return true;
		
	}
	

}
