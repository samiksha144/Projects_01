public class Cat extends Pets
{
    String sound;
    public Cat(String name) //Parameterized constructor
    {
        super(name);
    }  

    public Cat()
    {
        super("Cat name");
        sound = "Meow meow!"; 
    }

    public void makeSound() //Method overriding
    {
        System.out.println("Meow Meow !");
    }
}
