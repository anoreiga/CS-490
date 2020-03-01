package program1;
import java.util.*;
public class minHeap
{
    private ArrayList<int[]> nodes = new ArrayList<>();
    private int size;
    private int maxsize;

    private static final int FRONT = 1;
    private int parent (int position)
    {
        return position / 2;
    }

    private int leftChild(int position)
    {
        return (2 * position);
    }

    private int rightChild(int position)
    {
        return (2 * position) + 1;
    }

    private boolean isLeaf(int position)
    {
        if(position >= (size/2) && position <= size)
        {
            return true;
        }
        return false;
    }

    private void swap(int position1, int position2)
    {
        Collections.swap(nodes, position1, position2);
    }

    private void reHeap(int position)
    {
        if(!isLeaf(position))
        {
            if (nodes.get(position)[0])
        }
    }
}
