public class Dog extends Pets //Hierarchical inheritance
{
    String sound;
    
    public Dog(String name) //Parameterized constructor
    {
        super(name);
    } 

    public Dog()
    {
        super("Dog name");
        sound = "Woof woof!"; 
    }

    public void makeSound() //Method overriding
    {
        System.out.println("Woof Woof !");
    }
}
