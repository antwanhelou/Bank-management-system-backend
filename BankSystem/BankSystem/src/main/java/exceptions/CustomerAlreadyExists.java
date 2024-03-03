package exceptions;

public class CustomerAlreadyExists extends  Exception{

    public CustomerAlreadyExists(String message)
    {
        super(message);
    }
}
