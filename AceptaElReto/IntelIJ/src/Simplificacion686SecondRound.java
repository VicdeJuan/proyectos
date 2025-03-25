import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Simplificacion686SecondRound {

    static final int[] digits = {1,2,3,4,5,6,7,8,9};
    static final int DIGITS = digits.length;

    public static List<Integer> getDigits(int num){
        List<Integer> retval = new LinkedList<Integer>();
        int i=0;

        while(num!=0){
            retval.add(num%10);
            num /= 10;
        }
        return retval;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        String[] leido;
        double num_val,den_val,val,num,den;

        int count = 0;
        boolean keep_going = true;
        boolean[] hasDigit = new boolean[10];
        for (int i = 0; i <= DIGITS; i++)
            hasDigit[i] = false;
        int digit_count = 0;
        int k = 2;
        boolean founded = true;
        while(true) {

            leido = sc.nextLine().split(" ");
            num_val = Integer.parseInt(leido[0]) * 1.0;
            den_val = Integer.parseInt(leido[1]) * 1.0;

            // Condición de parada
            if (num_val == 0 && den_val == 0)
                break;
            val = num_val / den_val;
            List<Integer> digits_num,digits_den;
            while(keep_going){
                digits_num = getDigits((int) num_val*k);
                digits_den = getDigits((int) den_val*k);

                if (digits_num.size() + digits_den.size() > 9) {
                    keep_going = false;
                    continue;
                }

                for (int d : digits_num){
                    if (hasDigit[d])
                        break;
                    else
                        hasDigit[d] = true;
                }
                for (int d : digits_den){
                    if (hasDigit[d])
                        break;
                    else
                        hasDigit[d] = true;
                }


                for (int i = 1; i < 10; i++)
                    if (!hasDigit[i])
                        founded = false;


                if (founded) {
                    count++;
                    //System.out.println("FOUND:  " + (int) num_val * k + "/" + (int) den_val * k);
                }
                /*else {
                    System.out.println("NOT:  " + (int) num_val * k + "/" + (int) den_val * k);
                }*/
                founded = true;
                for (int i = 0; i < 10; i++)
                    hasDigit[i] = false;
                k++;
            }
            k=2;
            keep_going = true;
            System.out.println(count);
            count=0;


        }
        sc.close();

    }

}
