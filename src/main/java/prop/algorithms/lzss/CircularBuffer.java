package prop.algorithms.lzss;
import prop.algorithms.lzss.KMP;

//********************************************************************
//  CircularArrayQueue.java       Authors: Lewis/Chase
//
//  Represents an array implementation of a queue in which the
//  indexes for the front and rear of the queue circle back to 0
//  when they reach the end of the array.
//  http://faculty.washington.edu/moishe/javademos/ch07%20Code/jss2/CircularArrayQueue.java
//********************************************************************
public class CircularBuffer {
    private final int DEFAULT_CAPACITY = 4096;
    public int front, rear, count;
    private char[] queue;


    //-----------------------------------------------------------------
    //  Creates an empty queue using the default capacity.
    //-----------------------------------------------------------------
    public CircularBuffer()
    {
        front = rear = count = 0;
        queue = (new char[DEFAULT_CAPACITY]);
    }

    //-----------------------------------------------------------------
    //  Creates an empty queue using the specified capacity.
    //-----------------------------------------------------------------
    public CircularBuffer (int initialCapacity)
    {
        front = rear = count = 0;
        queue = (new char[initialCapacity]);
    }

    //-----------------------------------------------------------------
    //  Adds the specified element to the rear of the queue, incrementing
    //  front if necessary.
    //-----------------------------------------------------------------
    public void enqueue (char element)
    {
//        if (size() == queue.length)
//            expandCapacity();

        queue[rear] = element;

        rear = (rear+1) % queue.length;
        if (rear == front) front = (front+1) %queue.length;

        count++;
    }

    //-----------------------------------------------------------------
    //  Removes the element at the front of the queue and returns a
    //  reference to it. Throws an EmptyCollectionException if the
    //  queue is empty.
    //-----------------------------------------------------------------
    public char dequeue()
    {
        if (isEmpty()) return '?';

        char result = queue[front];
        queue[front] = '-';

        front = (front+1) % queue.length;

        count--;

        return result;
    }

    //-----------------------------------------------------------------
    //  Returns a reference to the element at the front of the queue.
    //  The element is not removed from the queue.  Throws an
    //  EmptyCollectionException if the queue is empty.
    //-----------------------------------------------------------------
    public char first()
    {
        return queue[front];
    }

    //-----------------------------------------------------------------
    //  Returns true if this queue is empty and false otherwise.
    //-----------------------------------------------------------------
    public boolean isEmpty()
    {
        return front == rear;
    }

    //-----------------------------------------------------------------
    //  Returns the number of elements currently in this queue.
    //-----------------------------------------------------------------
    public int size()
    {
        return count;
    }


    //-----------------------------------------------------------------
    //  Returns a string representation of this queue.
    //-----------------------------------------------------------------
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        int i = front;
        while (i != rear) {
            result.append(queue[i]);
            i = (i+1) % queue.length;
        }
        return result.toString();

    }

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
