import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Common {

    public static void savePetDataToFile(Pets pet, PetsOwner owner) 
    {
        String fileName = owner.getPetOwnername() + "_data.txt"; // Use pet's name in the file name
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) 
        {
            writer.write("Pet Owner Name: " + owner.getPetOwnername());
            writer.newLine();
            writer.write("Pet Name: " + pet.getName());
            writer.newLine();
            writer.write("Hunger: " + pet.getHunger());
            writer.newLine();
            writer.write("Happiness: " + pet.getHappiness());
            writer.newLine();
            writer.write("Cleanliness: " + pet.getCleanliness());
            writer.newLine();
            writer.write("Energy: " + pet.getEnergy());
            writer.newLine();
            // Add more data as needed
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            System.out.println("Error saving pet data to file.");
        }
    }

}





