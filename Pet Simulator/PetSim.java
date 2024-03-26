import java.util.Scanner;

public class PetSim 
{
    public static void main(String[] args) 
    {
        Common dataSaver = new Common();
        Scanner scanner = new Scanner(System.in);
        
        while(true)
        {
        System.out.print("Enter your name: ");
        String petOwnerName = scanner.nextLine();
        PetsOwner ownerName = new PetsOwner(petOwnerName);

        System.out.println(ownerName.greetOwner());
        System.out.println(ownerName.greetOwner("Let's start with the virtual pet simulator!"));

        System.out.print("Enter the name of your pet: ");
        String petName = scanner.nextLine();
        Pets pet = new Pets(petName); //object

        boolean returnToPetSelection = false;

        while (!returnToPetSelection) 
        {
            System.out.println("\nSelect an action:");
            System.out.println("1. Feed");
            System.out.println("2. Play");
            System.out.println("3. Clean");
            System.out.println("4. Sleep");
            System.out.println("5. Check Status");
            System.out.println("6. Total number of pets");
            System.out.println("7. Exit");
            System.out.println("8. Main Menu");
            System.out.println("9. Return to pet selection");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    pet.feed();
                    break;
                case 2:
                    pet.play();
                    break;
                case 3:
                    pet.clean();
                    break;
                case 4:
                    pet.sleep();
                    break;
                case 5:
                    System.out.println(pet.getStatus());
                    break;
                case 6:
                    System.out.println(pet.getTotalPets());
                    break;
                case 7:
                    System.out.println("Goodbye!");
                    System.exit(0);
                case 8:
                    System.out.println("Main Menu");

                    scanner.nextLine(); // Consume newline
                    System.out.print("Do you want to return to the main menu? (yes/no): ");
                    String returnToMainMenu = scanner.nextLine();
             
                    if (!returnToMainMenu.equalsIgnoreCase("yes")) 
                    {
                     System.out.println("Goodbye!");
                     System.exit(0); // Exit the loop if user doesn't want to return to main menu
                    }
                    break;
                case 9:
                    returnToPetSelection = true;
                    scanner.nextLine();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            
            if (pet.hasWon()) 
            {
                System.out.println("Congratulations! You've won!");
                returnToPetSelection = true; // End the interaction loop
            }
                
            if (pet.hasLost()) 
            {
                System.out.println("Oh no! Your pet's hunger is critical. You've lost!");
                returnToPetSelection = true; // End the interaction loop
            }

            dataSaver.savePetDataToFile(pet,ownerName);
           
        }

    }

    
  }
}
    


