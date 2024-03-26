public class Bird extends Pets
{
    String sound;
    public Bird(String name) //Parameterized constructor
    {
        super(name);
    } 
    
    public Bird()
    {
        super("Bird name");
        sound = "Chirp chirp!"; 
    }

    public void makeSound() //Method overriding
    {
        System.out.println("Chirp Chirp!");
    }
}
