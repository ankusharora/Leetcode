package Docusign;

public class ProdutArrayItself {



    public int[] productExceptSelf2(int[] nums) {
        int i,temp=1;
        int n = nums.length;

        int prod[] = new int[n];

        for(int j=0;j<n;j++)
            prod[j] = 1;

        for(i=0;i<n;i++)
        {
            prod[i] = temp;
            temp = temp * nums[i];
        }

        temp = 1;

        for(i = n-1;i>=0;i--){
            prod[i] = prod[i] * temp;
            temp = temp * nums[i];
        }

        return prod;


    }
}
