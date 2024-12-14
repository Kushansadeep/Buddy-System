/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.main;

/**
 *
 * @author Kushan Sadeepa
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MemoryBlock {
    private int size;
    private boolean allocated;

    // Constructor to initialize the memory block
    public MemoryBlock(int size, boolean allocated) {
        this.size = size;
        this.allocated = allocated;
    }

    public MemoryBlock(int size) {
        this(size, false);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isAllocated() {
        return allocated;
    }

    public void setAllocated(boolean allocated) {
        this.allocated = allocated;
    }

    @Override
    public String toString() {
        return (allocated ? "Allocated" : "Free") + " Block: " + size + " KB";
    }
}

class BuddySystem {
    private int totalMemory;
    private List<MemoryBlock> memoryBlocks;

    // Constructor to initialize the Buddy System
    public BuddySystem(int totalMemory) {
        this.totalMemory = totalMemory;
        this.memoryBlocks = new ArrayList<>();
        this.memoryBlocks.add(new MemoryBlock(totalMemory)); // Start with a single block
    }

    // Allocate memory using the Buddy System
    public boolean allocate(int requestSize) {
        // Find the smallest power of 2 that can satisfy the request
        int size = 1;
        while (size < requestSize) {
            size *= 2;
        }

        // Find a suitable block
        for (MemoryBlock block : memoryBlocks) {
            if (!block.isAllocated() && block.getSize() >= size) {
                // Split the block if necessary
                while (block.getSize() > size) {
                    int buddySize = block.getSize() / 2;
                    memoryBlocks.add(new MemoryBlock(buddySize)); // Add new free block
                    block.setSize(buddySize); // Reduce the size of the current block
                }

                block.setAllocated(true); // Mark the block as allocated
                System.out.println("Allocated " + requestSize + " KB.");
                return true;
            }
        }

        System.out.println("Failed to allocate " + requestSize + " KB. Not enough space.");
        return false;
    }

    // Display the current state of memory blocks
    public void displayMemory() {
        System.out.println("Memory State:");
        for (MemoryBlock block : memoryBlocks) {
            String status = block.isAllocated() ? "Allocated" : "Free";
            System.out.println(" - 1 block of " + block.getSize() + " KB (" + status + ")");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get total memory size from user
        System.out.print("Enter the total memory size (in KB): ");
        int totalMemorySize = scanner.nextInt();
        BuddySystem buddySystem = new BuddySystem(totalMemorySize);

        // Display initial memory state
        System.out.println("Initial Memory State:");
        buddySystem.displayMemory();

        // Allow the user to enter exactly three memory requests
        for (int i = 0; i < 3; i++) {
            System.out.print("Enter memory request size " + (i + 1) + " (in KB): ");
            try {
                int requestSize = scanner.nextInt();
                buddySystem.allocate(requestSize);
                buddySystem.displayMemory(); // Display memory state after each allocation
            } catch (Exception e) {
                System.out.println("Please enter a valid integer.");
                scanner.next(); // Clear the invalid input
            }
        }

        // Final memory state
        System.out.println("\nFinal Memory State:");
        buddySystem.displayMemory();

        scanner.close();
    }
}
