package prop.algorithms.lzss;
import prop.algorithms.lzss.KMP;

/**
 * Representa un array implementat coma a buffer circular
 */
public class CircularBuffer {
    private final int DEFAULT_CAPACITY = 4096;
    public int front, rear, size;
    private char[] queue;


    /**
     * Creates an empty queue using the default capacity.
     */
    public CircularBuffer()
    {
        front = rear  = -1;
        size = DEFAULT_CAPACITY;
        queue = (new char[DEFAULT_CAPACITY]);
    }

    //-----------------------------------------------------------------
    //  Creates an empty queue using the specified capacity.
    //-----------------------------------------------------------------
    public CircularBuffer (int initialCapacity)
    {
        front = rear = -1;
        size = initialCapacity;
        queue = (new char[initialCapacity]);
    }

    /**
     * Adds the specified element to the rear of the queue.
     *
     * @param element
     */
    public void enqueue (char element)
    {
        //first case
        if (front == -1 && rear == -1) {
            front = rear = 0;
            queue[front] = element;
        }
        else {
            rear = (rear+1) % size;
            queue[rear] = element;

            if (front == rear) front = (front+1) % size;

        }




        //System.out.println("");
        //System.out.println(front);
        //System.out.println(rear);

    }


    /**
     * Returns a reference to the element at the front of the queue.
     * @return
     */
    public char first()
    {
        return queue[front];
    }

    /**
     * Returns the number of elements currently in this queue.
     */
    public int size()
    {
        return size;
    }


    /**
     * Returns a string representation of this queue.
     * @return
     */
    public String toString()
    {
        if (front == -1) return "";
        StringBuilder result = new StringBuilder();
        int i = front;
        do {
            result.append(queue[i]);
            i = (i+1) % queue.length;
        }
        while (i != (front) % size);
        return result.toString();

    }
    /**
     * Returns a string representation of this queue.
     * @return String
     */
    public String toString(int start, int len)
    {
        StringBuilder result = new StringBuilder();
        int i = start;
        int stop = (start+len) % queue.length;
        while (i != stop) {
            result.append(queue[i]);
            i = (i+1) % queue.length;
        }
        return result.toString();

    }


}
