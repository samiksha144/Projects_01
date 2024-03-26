import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class SortMaster extends JFrame 
{
    private JTextField inputField;
    private JTextArea outputArea;
    private JComboBox<String> algorithmComboBox;

    public SortMaster() 
    {
        setTitle("Sorting Game");
        setSize(700, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel inputLabel = new JLabel("Enter numbers separated by commas:");
        inputField = new JTextField(20);
        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(new SortButtonListener());

        // Adding algorithm selection dropdown
        String[] algorithms = {"Bubble Sort", "Selection Sort", "Insertion Sort",
                               "Merge Sort", "Quick Sort", "Heap Sort",
                               "Radix Sort", "Counting Sort", "Bucket Sort"};
        algorithmComboBox = new JComboBox<>(algorithms);

        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(algorithmComboBox);
        inputPanel.add(sortButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
    }

    private class SortButtonListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String input = inputField.getText();
            int[] numbers = Arrays.stream(input.split(","))
                                  .mapToInt(Integer::parseInt)
                                  .toArray();
            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
            SortingAlgorithm sortingAlgorithm = getSortingAlgorithm(selectedAlgorithm);

            if (sortingAlgorithm != null) 
            {
                sortingAlgorithm.sort(numbers);
                outputArea.setText(Arrays.toString(numbers));
            } 
            else 
            {
                outputArea.setText("Invalid sorting algorithm selected.");
            }
        }
    }

    // Helper method to get the selected sorting algorithm
    private SortingAlgorithm getSortingAlgorithm(String algorithmName) 
    {
        switch (algorithmName) 
        {
            case "Bubble Sort":
                return new BubbleSort();
            case "Selection Sort":
                return new SelectionSort();
            case "Insertion Sort":
                return new InsertionSort();
            case "Merge Sort":
                return new MergeSort();
            case "Quick Sort":
                return new QuickSort();
            case "Heap Sort":
                return new HeapSort();
            case "Radix Sort":
                return new RadixSort();
            case "Counting Sort":
                return new CountingSort();
            case "Bucket Sort":
                return new BucketSort();
            default:
                return null;
        }
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> {
            SortMaster gui = new SortMaster();
            gui.setVisible(true);
        });
    }
}

interface SortingAlgorithm 
{
    void sort(int[] array);
}

class BubbleSort implements SortingAlgorithm 
{
    public void sort(int[] array) 
    {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) 
        {
            for (int j = 0; j < n - i - 1; j++) 
            {
                if (array[j] > array[j + 1]) 
                {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}

class SelectionSort implements SortingAlgorithm 
{
    public void sort(int[] array) 
    {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) 
        {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) 
            {
                if (array[j] < array[minIndex]) 
                {
                    minIndex = j;
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }
}

class InsertionSort implements SortingAlgorithm 
{
    public void sort(int[] array) 
    {
        int n = array.length;
        for (int i = 1; i < n; i++) 
        {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) 
            {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }
}

class MergeSort implements SortingAlgorithm 
{
    public void sort(int[] array) 
    {
        mergeSort(array, 0, array.length - 1);
    }

    private void mergeSort(int[] array, int low, int high) 
    {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(array, low, mid);
            mergeSort(array, mid + 1, high);
            merge(array, low, mid, high);
        }
    }

    private void merge(int[] array, int low, int mid, int high) 
    {
        int[] temp = new int[array.length];
        for (int i = low; i <= high; i++) 
        {
            temp[i] = array[i];
        }
        int i = low, j = mid + 1, k = low;
        while (i <= mid && j <= high) 
        {
            if (temp[i] <= temp[j]) 
            {
                array[k] = temp[i];
                i++;
            } 
            else 
            {
                array[k] = temp[j];
                j++;
            }
            k++;
        }
        while (i <= mid) 
        {
            array[k] = temp[i];
            i++;
            k++;
        }
    }
}

class QuickSort implements SortingAlgorithm 
{
    public void sort(int[] array) 
    {
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] array, int low, int high) 
    {
        if (low < high) 
        {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    private int partition(int[] array, int low, int high) 
    {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) 
        {
            if (array[j] < pivot) 
            {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }
}


class HeapSort implements SortingAlgorithm 
{
    public void sort(int[] array) 
    {
        int n = array.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--)
        {
            heapify(array, n, i);
        }

        // Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) 
        {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            heapify(array, i, 0);
        }
    }

    void heapify(int[] array, int n, int i) 
    {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && array[left] > array[largest]) 
        {
            largest = left;
        }

        if (right < n && array[right] > array[largest]) 
        {
            largest = right;
        }

        if (largest != i) 
        {
            int temp = array[i];
            array[i] = array[largest];
            array[largest] = temp;

            heapify(array, n, largest);
        }
    }
}

class RadixSort implements SortingAlgorithm 
{
    public void sort(int[] array) 
    {
        int max = getMax(array);
        for (int exp = 1; max / exp > 0; exp *= 10) 
        {
            countSort(array, exp);
        }
    }

    private int getMax(int[] array) 
    {
        int max = array[0];
        for (int i = 1; i < array.length; i++) 
        {
            if (array[i] > max) 
            {
                max = array[i];
            }
        }
        return max;
    }

    private void countSort(int[] array, int exp) 
    {
        int n = array.length;
        int[] output = new int[n];
        int[] count = new int[10];
        Arrays.fill(count, 0);

        for (int i = 0; i < n; i++) 
        {
            count[(array[i] / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) 
        {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) 
        {
            output[count[(array[i] / exp) % 10] - 1] = array[i];
            count[(array[i] / exp) % 10]--;
        }

        for (int i = 0; i < n; i++) 
        {
            array[i] = output[i];
        }
    }
}

class CountingSort implements SortingAlgorithm 
{
    public void sort(int[] array) 
    {
        int n = array.length;
        int max = Arrays.stream(array).max().orElse(0);
        int[] count = new int[max + 1];

        for (int i = 0; i < n; i++) 
        {
            count[array[i]]++;
        }

        int index = 0;
        for (int i = 0; i <= max; i++) 
        {
            while (count[i] > 0) 
            {
                array[index++] = i;
                count[i]--;
            }
        }
    }
}


class BucketSort implements SortingAlgorithm 
{
    public void sort(int[] array) 
    {
        int n = array.length;
        int max = Arrays.stream(array).max().orElse(0);
        int min = Arrays.stream(array).min().orElse(0);

        int numBuckets = (max - min) / n + 1;
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<>(numBuckets);
        for (int i = 0; i < numBuckets; i++) 
        {
            buckets.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) 
        {
            int bucketIndex = (array[i] - min) / n;
            buckets.get(bucketIndex).add(array[i]);
        }

        for (ArrayList<Integer> bucket : buckets) 
        {
            Collections.sort(bucket);
        }

        int index = 0;
        for (ArrayList<Integer> bucket : buckets) 
        {
            for (int value : bucket) 
            {
                array[index++] = value;
            }
        }
    }
}
