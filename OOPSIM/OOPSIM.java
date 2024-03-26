// OOPSIM = OOPS Simulator. 
// OOPS simulator includes all the concepts of oops.

import java.util.Scanner;

abstract class Animal  //creating class concept, abstraction method 
{
    String name;
    static int animalCount = 0; //using static keyword

    public Animal(String name) //parametrized constructor
    {
        this.name = name;
        animalCount++;
    }

    public Animal(Animal otherAnimal) //copy constructor
    {
        this.name = otherAnimal.name;
        animalCount++;
    }

    public static int getAnimalCount() //using static keyword
    {
        return animalCount;
    }

    public abstract void makeSound(); //method, abstract method
    

    public void makeSound(int times) //method overloading
    {
        for (int i = 0; i < times; i++) 
        {
            System.out.println("Animal sound times");
        }
    }
}

class Dog extends Animal //Hierarchical Inheritance (multiple classes are derived from one base class)
{
    public Dog(String name)
    {
        super(name);
    }

    public void makeSound(int times) //method overloading
    {
        for (int i = 0; i < times; i++) 
        {
            System.out.println("Woof woof!");
        }
    }
}

class Cat extends Animal //Hierarchical Inheritance
{
    public Cat(String name)
    {
        super(name);
    }

    public void makeSound() //Method overriding
    {
        System.out.println("Meow!");
    }
}

class Fish
{
    String name;

    public Fish() //non-parametrized constructor
    {
        name = "Dolphin";
    }

     public void swim() {
        System.out.println("Fish is swimming.");
    }
}

class Bird extends Animal 
{
    public Bird(String name) 
    {
        super(name);
    }

    public Bird(Bird otherBird) 
    {
        super(otherBird); 
    }

    public void fly() 
    {
        System.out.println("Bird is flying.");
    }
}

class EncapsulatedAnimal 
{
    private String name;  // Using private access modifier to encapsulate the name attribute

    public EncapsulatedAnimal(String name) 
    {
        this.name = name;
    }

    public String getName() // Getter method to access the private name attribute
    {  
        return name;
    }

    public void setName(String name) // Setter method to modify the private name attribute
    {  
        this.name = name;
    }

    public void makeSound() 
    {
        System.out.println("Squeak!");
    }
}

abstract class Pen //Parent class
{
    String color;
    
    public Pen(String color)
    {
        this.color = color;
    }

}

class AnotherPen extends Pen //Child class - Single inheritance because there is only one base class which is inheriting properties from parent class.
{
    String name;
    
    public AnotherPen(String color,String name) 
    {
        super(color);
        this.name = name;
       
    }
    
}
abstract class Grandparent 
{
    String behaviour;

    public Grandparent(String behaviour)
    {
        this.behaviour = behaviour;
    }
    
}
class Parent extends Grandparent 
{
    public Parent(String behaviour)
    {
        super(behaviour);
    }

    
}
class Child extends Parent //Multilevel inheritance - Parent class is inherited from grandparent and child class is inherited from parent.
{
    String name;

    public Child(String behaviour,String name) 
    {
        super(behaviour);
        this.name = name;
    }
}
class GarbageCollector 
{
    public void runGarbageCollector() 
    {
        System.out.println("Running garbage collector...");
        System.gc();
        System.out.println("Garbage collector executed.");
    }
}

public class OOPSIM
{
    public static void main(String args[])
    {
        Scanner scanner = new Scanner(System.in);
        boolean continueSimulator = true; // Flag to control the loop

        System.out.println("Welcome to the OOPS Simulator!");

        GarbageCollector gcDemo = new GarbageCollector();

        while (continueSimulator)
        {
        System.out.println("1. Dog");
        System.out.println("2. Cat");
        System.out.println("3. Fish");
        System.out.println("4. Bird");
        System.out.println("5. Elephant");
        System.out.println("6. Pen");
        System.out.println("7. Child");
        System.out.println("8. Exit");
        System.out.print("Select an option to simulate: ");
        int choice = scanner.nextInt();

        Animal animal = null;

        switch(choice)
        {

        case 1:
        {
            Animal dog = new Dog("Duke"); //created object
            System.out.println("You selected: " + dog.name);
            dog.makeSound(2);
            break;
        } 
        case 2:
        {
            Animal cat = new Cat("Simba"); //created object
            System.out.println("You selected: " + cat.name);
            cat.makeSound();
            break;
        } 
        case 3: 
        {
           Fish fish = new Fish(); //created object

           System.out.println("You selected a fish: " + fish.name);
           fish.swim();
           break;
        } 
        case 4: 
        {
            Bird originalBird = new Bird("Robin");
            Bird copiedBird = new Bird(originalBird); // Use the copy constructor
            System.out.println("You selected a bird: " + copiedBird.name);
            copiedBird.fly();
            break;
        } 
        case 5:
        {
            EncapsulatedAnimal elephant = new EncapsulatedAnimal("Elephant");
            System.out.println("You selected: " + elephant.getName()); // Using getter to access the name
            elephant.makeSound();
            System.out.println("Total animals: " + Animal.getAnimalCount());
            break;
        }
        case 6:
        {
            
            AnotherPen anotherpen = new AnotherPen("Blue", "Butterflow");

            System.out.println("You selected : "+anotherpen.name);
            System.out.println("It's color is "+anotherpen.color);
            break;
            
        }
        case 7:
        {
            Child child = new Child("active", "Sam");

            System.out.println("Child name is : "+child.name);
            System.out.println("His behaviour is : "+child.behaviour);
            break;

        }
        case 8:
        {
            continueSimulator = false; // Exit the loop
            break;
        }
        default: 
        {
            System.out.println("Invalid choice.");
            break;
        }

     }
    if (!continueSimulator) 
    {
       System.out.println("Goodbye!");
       scanner.close();
    
    }
    
  }
}
}