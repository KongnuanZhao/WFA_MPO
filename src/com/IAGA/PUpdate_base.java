package com.IAGA;

public class PUpdate_base {
	
	/**
	 * ����ָ�����Paֵ
	 * @param current
	 * @param TM
	 * @param PM
	 * @return
	 */
	public double ComputPa(double current,double TM,double PM){
		return 0;
	}
	
	//ѡ�����ӣ����̶ķ���
	public boolean SelectionFit(int number,Double [][] bval,double [] fit,Double[][] newbval){
		return true;
	}
	
	//������Ⱥ�������ֵ��Χ
	public boolean ComputPR(Double [][] bval,int number,Double[][] PR){
		return true;
	}
	
	//ģ������ƽ������ӣ�SBX��
	public boolean CrossoverSBX(int number,double pc,Double[][] newbval){
		return true;
	}
	
	//������Ⱥ��������(SPSMO)
	public boolean MutationNonUPSO(int number,double pm,double current,double TM,double []bestv,double [][]UmaxUmin,Double[][] newbval){
		return true;
	}
	
	//�����������ӣ�IAO��
	public boolean IndiAdvantage(int number,double []bestv,Double[][] PRange,Double[][] newbval,double [][] UmaxUmin){
		return true;
		
	}
	

}
