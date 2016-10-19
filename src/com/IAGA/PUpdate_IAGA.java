package com.IAGA;

import java.util.Random;

public class PUpdate_IAGA extends PUpdate_base {

	@Override
	public double ComputPa(double current, double TM, double PM) {
		double pa = 0;
		// 分四阶段动态调控分割因子pa值
		if (current <= TM * 0.25) {
			pa = PM * 0.25;
		} else if (current > TM * 0.25 && current <= TM * 0.5) {
			pa = PM * 0.5;
		} else if (current > TM * 0.5 && current <= TM * 0.75) {
			pa = PM * 0.75;
		} else if (current > TM * 0.75) {
			pa = PM;
		}

		return pa;
	}

	@Override
	public boolean SelectionFit(int number, Double[][] bval, double[] fit,
			Double[][] newbval) {
		int N = bval.length; // 获取行数
		int PN = bval[0].length; // 获取列数
		double sum = 0; // 统计总适应度值
		double[] cump = new double[N]; // 累加适应度概率
		double[] fitp = new double[N]; // 适应度概率

		Random rd = new Random(); // 产生随机数[0,1]rd.NextDouble()
		for (int i = 0; i < N; i++) {
			sum += fit[i]; // 统计sum值
		}
		for (int i = 0; i < N; i++) {
			fitp[i] = fit[i] / sum;// 计算适应度概率
		}

		cump[0] = fitp[0];
		for (int i = 1; i < N; i++) {
			cump[i] = cump[i - 1] + fitp[i];
		}
		for (int i = 0; i < number; i++) {
			int n = 0;
			double m = rd.nextDouble();
			for (int j = 0; j < N; j++) {
				if (m < cump[j]) {
					n = j;
					break;
				}
			}
			// newfit[n] = fit[n];
			for (int j = 0; j < PN; j++) {
				newbval[i][j] = bval[n][j];
			}
		}
		return true;
	}

	@Override
	public boolean ComputPR(Double[][] bval, int number, Double[][] PRange) {
		int N = bval.length;// 获取行数
		int PN = bval[0].length;// 获取列数
		if (PRange.length == 2) {
			// 判断PRange有两行
			for (int i = 0; i < PN; i++) {
				double tempMax = bval[0][i];
				double tempMin = bval[0][i];
				for (int j = 0; j < number; j++) {
					if (bval[j][i] > tempMax) {
						tempMax = bval[j][i];// 更新参数最大值
					}
					if (bval[j][i] < tempMin) {
						tempMin = bval[j][i];// 更新参数最小值
					}
				}
				PRange[0][i] = tempMax;
				PRange[1][i] = tempMin;
			}
		}
		return true;
	}

	@Override
	public boolean CrossoverSBX(int number, double pc, Double[][] newbval) {
		// 交叉算子（模拟二进制交叉（SBX））
		int PN = newbval.length;
		double const1 = 1.0 / 3;
		// double fm;//记录交叉两个体中最大值
		// double pc1 = 0;//记录自适应的交叉概率
		double alfa = 0;
		Random rd = new Random();// 产生随机数[0,1]rd.NextDouble()
		for (int i = 0; i < number; i = i + 2) {
			// 以下为自适应交叉概率，不合适删除（庄嘉祥，2012.10.4）
			/*
			 * fm = newfit[i] > newfit[i + 1] ? newfit[i] : newfit[i + 1]; if
			 * (fm >= favg) { pc1 = pc - 0.3 * (fm - favg) / (bestf - favg); }
			 * else { pc1 = pc; }
			 */
			for (int j = 0; j < PN; j++) {
				// if (rd.NextDouble() < pc1)修改（庄嘉祥，2012.10.4）
				if (rd.nextDouble() < pc) {
					double u = rd.nextDouble();
					if (u <= 0.5) {
						alfa = Math.pow(2 * u, const1);
					} else {
						alfa = Math.pow(2 * (1 - u), const1);
					}
					double x1 = newbval[i][j];
					double x2 = newbval[i + 1][j];
					newbval[i][j] = 0.5 * ((1 - alfa) * x1 + (1 + alfa) * x2);
					newbval[i + 1][j] = 0.5 * ((1 + alfa) * x1 + (1 - alfa)
							* x2);
				}
			}
		}
		return true;
	}

	@Override
	public boolean MutationNonUPSO(int number, double pm, double current,
			double TM, double[] bestv, double[][] UmaxUmin, Double[][] newbval) {
		// 半粒子群变异算子(SPSMO)
		int PN = newbval[0].length;
		double constb = 3.0;
		// double pm1 = 0;//记录自适应的变异概率
		double alfa = 0;
		Random rd = new Random();// 产生随机数[0,1]rd.NextDouble()
		for (int i = 0; i < number; i++) {
			for (int j = 0; j < PN; j++) {
				if (pm > rd.nextDouble()) {
					double emp = newbval[i][j];
					double u = rd.nextDouble();
					if (u < 0.5) {
						double r = rd.nextDouble();
						alfa = (UmaxUmin[j][0] - emp)
								* (1 - Math.pow(r, 1.0
										- ((double) current / TM) * constb));
					} else {
						double r = rd.nextDouble();
						alfa = (UmaxUmin[j][1] - emp)
								* (1 - Math.pow(r, 1.0
										- ((double) current / TM) * constb));
					}
					newbval[i][j] = newbval[i][j] + alfa + (bestv[j] - emp)
							* rd.nextDouble() * 2;
					if (newbval[i][j] > UmaxUmin[j][0]
							|| newbval[i][j] < UmaxUmin[j][1])// 越界处理
					{
						if (rd.nextDouble() >= 0.5) {
							newbval[i][j] = (UmaxUmin[j][0] + UmaxUmin[j][1]) / 2;
						} else {
							newbval[i][j] = emp;
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean IndiAdvantage(int number, double[] bestv, Double[][] PRange,
			Double[][] newbval, double[][] UmaxUmin) {
		// 个体优势算子(IAO)
		int N = newbval.length; //获取行数
		int PN = newbval[0].length; //获取列数
		Random rd = new Random(); //产生随机数[0,1]rd.NextDouble()
		for (int i = number; i < N - 1; i++) {
			for (int j = 0; j < PN; j++) {
				newbval[i][j] = bestv[j] + (PRange[0][j] - PRange[1][j])
						* (rd.nextDouble() - 0.5) / 5;

				while (newbval[i][j] > UmaxUmin[j][0]
						|| newbval[i][j] < UmaxUmin[j][1]) //越界处理
				{
					double pos = rd.nextDouble();
					if (pos <= 0.5) {
						// newbval[i][j] = (PRange[0][j] + PRange[1][j]) / 2;
						newbval[i][j] = bestv[j];

					}

					else {
						newbval[i][j] = bestv[j]
								+ (PRange[0][j] - PRange[1][j])
								* (rd.nextDouble() - 0.5) / 5;
					}

				}
			}
		}
		for (int j = 0; j < PN; j++) {
			newbval[N - 1][j] = bestv[j];// 赋值最优解
		}
		return true;
	}

}
