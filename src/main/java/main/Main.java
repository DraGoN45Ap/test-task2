package main;

import main.entity.OperationDTO;
import main.service.OperationService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Main class.
 */
public class Main {

    /**
     * Variable for identify current user folder.
     */
    public static final String USER_DIR = System.getProperty("user.dir").concat( "\\");

    public static void main(String[] args) {
        if (args[0] == null) {
            System.out.println("Operations file not found");
            return;
        } else if (args[1] == null) {
            System.out.println("Output filename for calculate sum by dates not found");
            return;
        } else if (args[2] == null) {
            System.out.println("Output filename for calculate sum by offices not found");
            return;
        }
        String operationsFilename = args[0];
        String sumsByDatesFilename = args[1];
        String sumsByOfficesFilename = args[2];
        File file = new File(USER_DIR.concat(operationsFilename));
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            List<OperationDTO> operationList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] operationLine = line.split(" ");
                OperationDTO operationDTO = new OperationDTO(
                        LocalDateTime.parse(operationLine[0]),
                        Integer.valueOf(operationLine[1]),
                        Integer.valueOf(operationLine[2]),
                        Double.valueOf(operationLine[3]));
                operationList.add(operationDTO);
            }
            CompletableFuture<String> calculateTotalPriceByDays = CompletableFuture
                    .supplyAsync(() ->
                            OperationService.calculateTotalPriceByDays(
                                    new ArrayList<>(operationList), sumsByDatesFilename));
            CompletableFuture<String> calculateTotalPriceByPointOfSales = CompletableFuture
                    .supplyAsync(() ->
                            OperationService.calculateTotalPriceByPointOfSale(
                                    new ArrayList<>(operationList), sumsByOfficesFilename));
            System.out.println(calculateTotalPriceByDays.get());
            System.out.println(calculateTotalPriceByPointOfSales.get());
        } catch (IOException | ExecutionException | InterruptedException e) {
            System.out.println("Cannot read file: ".concat(operationsFilename).concat(e.getMessage()));
        }
    }
}