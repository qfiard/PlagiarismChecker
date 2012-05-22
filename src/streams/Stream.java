package streams;

public interface Stream<E> {
	
	// This method tells whether the stream is non - empty ,
	// that is , whether the stream has a first element .
	public boolean hasNext ();
	
	// This method can be called only if [ hasNext ] has been
	// invoked and has returned [ true ]. It returns the first
	// element of the stream . This element is consumed , that
	// is , it is taken out of the stream .
	public E next ();

}
