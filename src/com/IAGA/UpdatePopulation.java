package com.IAGA;

/**
 * 
 * @author zhaokongnuan IAGA�㷨��Ⱥ���´��� �汾��PUpdate-IAGA_1.0.0
 * ���룺�������ಿ�ֲ��������̶���Ʒ�ֲ����������� 
 * ���������Ӱ�챾ģ�͵ļ�������������ۼ�������ۼ�������ͨ���ӿڴ���
 *
 */
public class UpdatePopulation {
	 //�㷨����
	PUpdate_IAGA mIAGA = new PUpdate_IAGA();
	 //�㷨����
	protected double pc = 0; //�������
	protected double pm = 0; //�������
	protected double PA = 0; //�ָ�����paֵ���ֵ
	protected double TM = 0; //����������

	/**
	 * �������
	 */
	 //���������������
	String _Name = "PUpdate-IAGA_1.0.0"; //����
	String _Time = "20121004"; //����ʱ��
	String _Ver = "Ver 1.0.0"; //�汾��
	String _OutErr = ""; //������Ϣ���

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

	 //��ʼ��������㷨����
	public boolean IniAlgoPara(double[] Para) {
		if (Para.length == 4) {
			pc = Para[0];
			pm = Para[1];
			PA = Para[2];
			TM = Para[3];
			return true;
		} else {
			_OutErr = "�㷨�����������ԣ�";
			return false;
		}

	}

	/**
	 * IAGA��Ⱥ���¶��⺯��
	 * 
	 * @param current
	 *            Ŀǰ��Ⱥ��������
	 * @param UmaxUmin
	 *            [][] ������Χ
	 * @param beatv
	 *            [] ��Ⱥ���Ÿ���
	 * @param bval
	 *            [][] ��Ⱥ��Ϣ
	 * @param fit
	 *            []
	 * @return bval[][]�����º����Ⱥ��Ϣ
	 * @return boolean
	 */
	public boolean UpdatePopu(double current, double[][] UmaxUmin,
			double[] bestv, Double[][] bval, double[] fit) {
		int N = bval.length; //��ȡ���� ��Ⱥ��С
		int PN = bval[0].length; //��ȡ���� ��������
		double pa = 0; //�ָ�����
		Double[][] newbval = new Double[N][PN]; //����Ⱥ
		Double[][] PRange = new Double[2][PN]; //�µĲ�����Χ

		pa = mIAGA.ComputPa(current, TM, PA); //����ָ�����pa
		int number = (int) (N - N * pa); //��¼ѡ�������Ŀ
		mIAGA.SelectionFit(number, bval, fit, newbval); //ѡ������(���̶ķ���)
		mIAGA.ComputPR(newbval, number, PRange); //������Ⱥ�������ֵ��Χ
		mIAGA.CrossoverSBX(number, pc, newbval); //ģ������ƽ�������(SBX)
		mIAGA.MutationNonUPSO(number, pm, current, TM, bestv, UmaxUmin, newbval); //������Ⱥ��������(SPSMO)
		mIAGA.IndiAdvantage(number, bestv, PRange, newbval, UmaxUmin); //������������(IAO)
		// ���º���Ⱥ����ԭ��Ⱥbval������bval
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < PN; j++) {
				bval[i][j] = newbval[i][j];
			}
		}
		return true;
	}

}
