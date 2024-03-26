public class PetsOwner 
{

    String name;

    public PetsOwner() //non-parametrized constructor
    {
        name = "Unknown";
    }

    public PetsOwner(String petOwnerName) //parametrized constructor
    {
        
        this.name = petOwnerName;
        
    }

    public String getPetOwnername()
    {
        return name;
    }

    public String greetOwner() //method overloading
    {
        return "Hello, " + name + "!";
    }

    public String greetOwner(String greet) 
    {
        return "Let's start with the virtual Pet Simulator !";
    }
    
}
