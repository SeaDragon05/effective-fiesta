package AI;

import java.lang.reflect.Array;
/**
 * <h1>Matrix class</h1>
 * <p>This class uses generic types to help diversify its usage.</p>
 * <h2>Usage:</h2>
 * <p>To use the {@code Matrix} class, you will need to define the type of object to be stored:
 * <p><code>Matrix<*Object> Example = new Matrix<*Object>(3, 3, *Object.class);</code>
 * <p>The above code constructs a new Matrix object that stores the class Object in a 3x3 Matrix
 * <p>This class does not implement advanced Matrix functions
 * @param <T>
 */
public class Matrix<T> {
	/**
	 * The Matrix itself
	 */
	protected T[][] matrix;
	/**
	 * Easy access data
	 */
	private int Y, X;
	
	/**
	 * Constructs a Matrix class with given Class type and 
	 * @param Y
	 * @param X
	 * @param clazz
	 */
	@SuppressWarnings("unchecked")
	public Matrix(int Y, int X, Class<T> clazz) {
		this.matrix = (T[][]) Array.newInstance(clazz, Y, X);
		this.Y = Y;
		this.X = X;
	}

	/**
	 * Override this class to implement how you add the given object class
	 * @param y
	 * @param x
	 * @param Value
	 */
	public void add(int y, int x, T Value) {}//to be overridden
	
	/**
	 * Gets the object stored from the specified location
	 * @param y
	 * @param x
	 * @return
	 */
	public T get(int y, int x) {
    	if (y < 0 || y > this.Y)
    		throw new IndexOutOfBoundsException(y + " out of bound for length: " + this.Y);
    	if (x < 0 || x > this.X)
    		throw new IndexOutOfBoundsException(x + " out of bound for length: " + this.X);
        return matrix[y][x];
    }

	/**
	 * Sets the given value at the specified location
	 * @param y
	 * @param x
	 * @param value
	 */
    public void set(int y, int x, T value) {
    	if (y < 0 || y > this.Y)
    		throw new IndexOutOfBoundsException("Y out of bound for length: " + this.Y);
    	if (x < 0 || x > this.X) 
    		throw new IndexOutOfBoundsException("X out of bound for length: " + this.X);
        matrix[y][x] = value;
    }
    
    /**
     * Sets the given value to every index in the entire indicated row
     * @param y
     * @param value
     */
    public void setRow(int y, T value) {
    	if (y < 0 || y > this.Y)
    		throw new IndexOutOfBoundsException("Y out of bound for length: " + this.Y);
    	for (int i = 0; i < this.X; i++)
    		matrix[y][i] = value;
    }

    /**
     * Returns the amount of rows
     * @return
     */
    public int getRows() {
        return Y;
    }

    /**
     * Returns the amount of columns
     * @return
     */
    public int getColumns() {
        return X;
    }
}
