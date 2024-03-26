public class Pets 
{
    public String name;
    public int hunger;
    public int happiness;
    public int cleanliness;
    public int energy;

    private static int totalPets = 0; // Counter for total number of pets created, using static keyword
    

    public Pets(String name) //Parameterized constructor
    {
        this.name = name;
        hunger = 50;
        happiness = 50;
        cleanliness = 50;
        energy = 50;
        totalPets++;
    }

    public void feed() //method
    {
        hunger -= 20; // Decrease hunger by 20 points
        happiness += 10; // Increase happiness by 10 points after being fed
        energy += 20; // Increase energy by 10 points after being fed

        if (hunger < 0) 
        {
            hunger = 0; // Ensure hunger doesn't go negative
        }

        if(happiness > 100 || energy > 100)
        {
            happiness = 100;
            energy = 100;
        }
        
    }

    public void play() 
    {
        happiness += 20; // Increase happiness by 20 points 
        energy -= 10;
        cleanliness -= 10; // Playing might make the environment a bit less clean
        hunger -= 20;

        if(energy < 0 || cleanliness <0)
        {
            energy = 0;
            cleanliness = 0;
        }

        if(happiness > 100)
        {
            happiness = 100;
        }

    }

    public void clean() 
    {
        cleanliness += 30; // Increase cleanliness by 30 points after cleaning

        if (cleanliness > 100) 
        {
            cleanliness = 100; // Ensure cleanliness doesn't exceed 100
        }
    }

    public void sleep() 
    {
        energy += 30;
        happiness += 10;

        if(energy > 100 || happiness > 100 )
        {
            energy = 100;
            happiness = 100;
        }
    }

    public String getStatus() 
    {
        return "Name: " + name +
               "\nHunger: " + hunger +
               "\nHappiness: " + happiness +
               "\nCleanliness: " + cleanliness +
               "\nEnergy: " + energy;
    }

    public static int getTotalPets()
    {
        return totalPets;
    }

    public boolean hasWon() 
    {
        return happiness >= 90 && cleanliness >= 90 && energy >= 90;
    }

    public boolean hasLost() 
    {
        return hunger <= 0;
    }

    public String getName() 
    {
        return name;
    }

    public int getHunger()
    {
        return hunger;
    }

    public int getHappiness()
    {
        return happiness;
    }

    public int getCleanliness()
    {
        return cleanliness;
    }

    public int getEnergy()
    {
        return energy;
    }

    public void makeSound() 
    {
        System.out.println("Animal makes a sound");
    }

} 

