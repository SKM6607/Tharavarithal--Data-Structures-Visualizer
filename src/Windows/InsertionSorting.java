package Windows;

import java.util.Arrays;
import java.util.Random;

public class InsertionSorting {
    static final int[] array={
            324,23,2,14,2,85,73,2,34,56
    };
   static void quickSort(int[] array){
        try {
            quickSortHelper(array,0,array.length-1);
        }catch (StackOverflowError e){
            e.printStackTrace();
        }
    }
   static void swap(int[] array,int a,int b){
        int temp=array[a];
        array[a]=array[b];
        array[b]=temp;
    }
   static void quickSortHelper(int[] array,int low,int high){
        if(low<high){
            int p=partition(array,low,high);
            quickSortHelper(array,low,p-1);
            quickSortHelper(array,p+1,high);
        }
    }
 static int partition(int[] array,int low,int high){
        int pivot=array[(low+high)/2];
        int j=low-1;
        for (int i = low; i < high; i++) {
            if(array[i]<pivot){
                j++;
                swap(array,i,j);
            }
        }
        swap(array,j+1,high);
        return j+1;
    }
    static long[] returnTimeTaken(int size){
       int[] array=new int[size];
       Random random=new Random();
       long[] retArray=new long[2];
       for (int i=0;i<size;i++){
           array[i]=random.nextInt(0,1000);
       }
       long start=System.currentTimeMillis();
       quickSort(array);
       long end=System.currentTimeMillis();
       retArray[0]=end-start;
        for (int i=0;i<size;i++){
            array[i]=random.nextInt(0,1000);
        }
       start=System.currentTimeMillis();
       for(int i=0;i<array.length;i++){
           int min=i;
           for (int j = i+1; j < array.length; j++) {
               if(min>array[j]){
                   swap(array,i,j);
                   min=j;
               }
           }
           swap(array,i,min);
       }
       end=System.currentTimeMillis();
       retArray[1]=0;
       return retArray;
    }
    public static void main(String[] args) {
       quickSort(array);
        System.out.println(Arrays.toString(array));
    }
}
