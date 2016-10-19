package com.tools;

import java.util.List;
import java.util.Random;


public class DataManagement {

	private DataSet myDataSet;// ���ݼ�

	public DataSet getMyDataSet() {
		return myDataSet;
	}

	public void setMyDataSet(DataSet myDataSet) {
		this.myDataSet = myDataSet;
	}

	// Ʒ������(RiceGrow��ORYZA2000����)
	private String riceName;// ˮ��Ʒ��
	private String plantStyle;// ��ֲ��ʽ
	private String plantPlace;// ��ֲ�ص�
	private double plantDimension;// ��ֲά�� �����������γ�Ȼ���ά�Ȱ�������
	private double plantDepth;// ��ֲ���
	private int Tempguanli;// �¶ȹ�����
	
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

	public String strWeatherFile;// �����ļ� ����
	public String strPHEFile;// ������ļ� ����
	public String strWeatherFile_dp;// �����ļ� ���
	public String strPHEFile_dp;// ������ļ� ���
	public String strInfoFile;// ����������Ӧ���������ֵ��

	public List<String> strPlace;// //����Ѽ������������ݵص�
	public int internodeNumber;// �����ڼ���
	public double lowestTemp;// ����¶�

	public String[] strPHE = { "������", "������", "�ν���", "������", "������", "������", "�ཬ��",
			"������" };
	public String[] strPHEO = { "������", "������", "������", "������" };

	// ģ������
	private double[][] paraRange;// RiceGrow������Χ
	private int[] PHE;// RiceGrow�������

	private double[][] paraRangeO;// ORYZA2000������Χ
	private int[] PHEO;// ORYZA2000�������
	private int ismodel;// n:1ΪRiceGrow_POT��Ԥ�⣬2ΪRiceGrow_HDD��Ԥ��,3ΪORYZA_POT��Ԥ��
	private boolean isYSFH; // �Ƿ���������ֻ�
	
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

	

	// �㷨����
	public double[] sg_SPHE;// ���������
	public double[] sg_wPHE;// �����Ȩ��

	public int sg_TM;
	public int sg_ParaNum;
	public int sg_BvalNum;
	public int sg_LoopTime;// �ظ�ʵ�����
	public double sg_Pc;
	public double sg_Pa;
	public double sg_Pm;
	public double[][] sg_dRange;// ������Χ
	public int sg_internodeNumber;// �ڼ�
	public int sg_DNumber;// �������
	public List<String> Dplace;// ���εص�
	public double sg_Pmw;
	public double sg_Pmb;
	public double sg_Pcb;
	public double sg_Pcw;
	public int sg_M;
	public double sg_Pcu;

	// ������������
	public List<String[]> sObjInManagment;
	public List<Integer> RandList;

	// ��������
	public List<String[][]> MetoManagment;
	public String[][] Meto;
	public String[] ObjIn;

	public int[] getPHEO() {
		return PHEO;
	}

	public void setPHEO(int[] pHEO) {
		PHEO = pHEO;
	}

	// �����ʵ��ֵ
	public List<Double[]> PHEManagment;
	public int isShockFlag = 0;
	public int[][] ODPHE;// ����ڹ۲���ʵ����
	public double[][] TMAX;// ��������TMAX���洢���������е��������(����) ������ʲô��˼��
	public double[][] TMIN;// ��������TMIN���洢���������е��������(����)

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
		//����������ֻ�   �������ֹ����ã����������Ǹ��ݾ��ڼ������жϣ�����
		if(isYSFH == true){
			strPHE[2]="��ֻ�";
		}else{
			strPHE[2]="�ν���";
		}		
	}
	
	/**
	 * IAGA����
	 */
	//��������
	public boolean loadDataIAGA(double [] m_paraIAGA,int m_ismodel){
		ismodel = m_ismodel;
		if(m_paraIAGA.length == 5){
			return true;
		}else{
			return false;
		}
	}
	

}
