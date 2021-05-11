package exceptions;

public class TeamAlreadyExists extends Exception {
     private static final long serialVersionUID = 1L;

     public TeamAlreadyExists()
      {
        super();
      }
      /**This exception is triggered if the question already exists 
      *@param s String of the exception
      */
      public TeamAlreadyExists(String s)
      {
        super(s);
      }
}