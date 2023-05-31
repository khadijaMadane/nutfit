package com.example.nutfit;

public class phase1 {
    private static final double EPSILON = 1.0E-8;/**/
    double[][] table3; // tableaux
    int l2; // number of constraints
    int c2; // number of original variables
    int nNut,nIng;
    /*NEW*/int[] basis;
    public phase1(double[][] table3, int l2,int c2,int nNut,int nIng) {
        this.l2 = l2;
        this.c2 = c2;
        this.table3 = table3;
        this.nNut=nNut;
        this.nIng=nIng;
        /*NEW*/
        basis = new int[nNut];
        for (int i = 0; i < nNut; i++)
            basis[i] = nIng + i;

        solve();
    }
    //solution optimal ou non
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
        if (table3[l2-1][c2-1] > EPSILON) throw new ArithmeticException("Linear program is infeasible");/**/
    }
    // print tableaux
    void show() {
        System.out.println("M = " + l2);
        System.out.println("N = " + c2);
        for (int i = 0; i < l2; i++) {
            for (int j = 0; j <c2; j++) {
                System.out.print(table3[i][j]+"        ");
            }
            System.out.println();
        }

        /*NEW*/
        //System.out.println("value = " + table3[l2][c2]);
        for (int i = 0; i < nNut; i++)
            if (basis[i] < nIng)
                System.out.println("x_"+ basis[i] + " = "+ table3[i][nNut+ nIng+1]);
        System.out.println();

    }
    // index of a non-basic column with most positive cost
    public int dantzig() {
        int q = 0;
        /*if(table3[l2-1][c2-1]<=0)*/
        for (int j = 0; j <c2-1; j++)
            if (table3[l2-1][j] < table3[l2-1][q] ) {
                System.out.println("z="+ table3[l2-1][c2-1]+"    z="+q);
                q = j;
            }
        if (table3[l2-1][q] >= 0) {
            System.out.println("z="+ table3[l2-1][c2-1]);
            return -1; // optimal
        }else {
            System.out.println("z="+ table3[l2-1][c2-1]);
            return q;
        }
    }
    // find row p using min ratio rule (-1 if no such row)
    public int minRatioRule(int q) {
        int p = -1;
        for (int i = 0; i < l2-2; i++) {
            if (table3[i][q] <= 0)
                continue;
            else if (p == -1)
                p = i;
            else if ((table3[i][c2-1] / table3[i][q]) < (table3[p][c2-1] / table3[p][q]))
                p = i;
        }
        return p;
    }
    // pivot on entry (p, q) using Gauss-Jordan elimination
    public void pivot(int p, int q) {
        // everything but row p and column q
        for (int i = 0; i < l2; i++)
            for (int j = 0; j < c2; j++)
                if (i != p && j != q)
                    table3[i][j] -= table3[p][j] * table3[i][q]/ table3[p][q];
        // zero out column q
        for (int i = 0; i < l2; i++)
            if (i != p)
                table3[i][q] = 0.0;
        // scale row p
        for (int j = 0; j <c2; j++)
            if (j != q)
                table3[p][j] /= table3[p][q];
        table3[p][q] = 1.0;
    }

    /*NEW*/
    // return primal solution vector
    public double[] primal() {
        double[] x = new double[nIng];
        for (int i = 0; i < nNut; i++)
            if (basis[i] < nIng)
                x[basis[i]] = table3[i][nNut+ nIng];
        return x;
    }
    public int[] basis(){
        return basis;

    }

//////////////////fin//////////////////////////////////////////////////
}