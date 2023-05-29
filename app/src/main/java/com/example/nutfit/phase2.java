package com.example.nutfit;

public class phase2 {
    double[][] table5; // tableaux
    int l2,new_l2; // number of constraints
    int c2,new_c2; // number of original variables
    int nNut,nIng;
    /*NEW*/int[] basis;
    public phase2(double[][] table5, int new_l2,int new_c2,int nNut,int nIng,int[] basis) {
        this.new_l2 = new_l2;
        this.new_c2 = new_c2;
        this.table5 = table5;
        this.nNut=nNut;
        this.nIng=nIng;
        this.basis=basis;
        /*NEW*/
       // basis = new int[nNut];
     //   for (int i = 0; i < nNut; i++)
        //    basis[i] = nIng + i;

        solve();
    }
    public void solve() {
        while (true) {
            show();
            int q = 0;
            // find entering column q
            q = dantzig();
            if (q == -1)
                break; // optimal
            // find leaving row p
            int p = minRatioRule(q);
            if (p == -1)
                throw new ArithmeticException("Linear program is unbounded");
            // pivot
            pivot(p, q);

            /*NEW*/
            // update basis
            basis[p] = q;
        }
    }
    // index of a non-basic column with most positive cost
    public int dantzig() {
        int q = 0;
        for (int j = 1; j < new_c2-1; j++)
            if (table5[new_l2-1][j] > table5[new_l2-1][q])
                q = j;
        if (table5[new_l2-1][q] <= 0)
            return -1; // optimal
        else
            return q;
    }

    // find row p using min ratio rule (-1 if no such row)
    private int minRatioRule(int q) {
        int p = -1;
        for (int i = 0; i < new_l2-1; i++) {
            if (table5[i][q] <= 0)
                continue;
            else if (p == -1)
                p = i;
            else if ((table5[i][new_c2-1] / table5[i][q]) < (table5[p][new_c2-1] / table5[p][q]))
                p = i;
        }
        return p;
    }

    // pivot on entry (p, q) using Gauss-Jordan elimination
    private void pivot(int p, int q) {
        // everything but row p and column q
        for (int i = 0; i < new_l2; i++)
            for (int j = 0; j < new_c2; j++)
                if (i != p && j != q)
                    table5[i][j] -= table5[p][j] * table5[i][q]/ table5[p][q];
        // zero out column q
        for (int i = 0; i < new_l2; i++)
            if (i != p)
                table5[i][q] = 0.0;
        // scale row p
        for (int j = 0; j <new_c2; j++)
            if (j != q)
                table5[p][j] /= table5[p][q];
        table5[p][q] = 1.0;
    }

    // print tableaux
    void show() {
        System.out.println("M = " + new_l2);
        System.out.println("N = " + new_c2);
        for (int i = 0; i < new_l2; i++) {
            for (int j = 0; j <new_c2; j++) {
                System.out.printf("%7.4f ",table5[i][j]);
            }
            System.out.println();
        }
        /*NEW*/
        System.out.println("value = " + table5[new_l2-1][new_c2-1]);
        for (int i = 0; i < nNut; i++)
            if (basis[i] < nIng)
                System.out.println("x_"+ basis[i] + " = "+ table5[i][nNut+ nIng-1]);
        System.out.println();
    }

    /*NEW*/
    // return primal solution vector
    public double[] primal() {
        double[] x = new double[nIng];
        for (int i = 0; i < nNut; i++)
            if (basis[i] < nIng)
                x[basis[i]] = table5[i][nNut+ nIng];
        return x;
    }
    public int[] basis(){
        return basis;

    }

/////////////////////////////fin/////////////////////////////////////////
}