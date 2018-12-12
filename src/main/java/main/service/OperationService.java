package main.service;

import main.entity.AbstractSumDTO;
import main.entity.OperationDTO;
import main.entity.SumByDate;
import main.entity.SumByPointOfSale;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static main.Main.USER_DIR;

/**
 * Operation service.
 */
public class OperationService {

    /**
     * Singleton constructor.
     */
    private OperationService() {
    }

    /**
     * Calculate total price by days by list of operations.
     *
     * @param operationList       list of operation data.
     * @param sumsByDatesFilename output filename.
     * @return status.
     */
    public static String calculateTotalPriceByDays(List<OperationDTO> operationList,
                                                   String sumsByDatesFilename) {
        try (FileWriter sumsByDatesWriter = new FileWriter(USER_DIR.concat(sumsByDatesFilename))) {
            List<SumByDate> sumByDatesList = createTargetSumList(operationList, SumByDate.class);
            sumByDatesList.sort(Comparator.comparing(SumByDate::getDate));
            sumByDatesList.forEach(s -> saveToTxt(sumsByDatesWriter, s.toString()));
            return "Calculate sum by dates is done";
        } catch (IOException e) {
            System.out.println("Cannot write total price by days into file: ".concat(sumsByDatesFilename));
        }
        return null;
    }

    /**
     * Calculate total price by point of sale.
     *
     * @param operationList         list of operation data.
     * @param sumsByOfficesFilename output filename.
     * @return status.
     */
    public static String calculateTotalPriceByPointOfSale(List<OperationDTO> operationList,
                                                          String sumsByOfficesFilename) {
        try (FileWriter sumsByOfficesWriter = new FileWriter(USER_DIR.concat(sumsByOfficesFilename))) {
            List<SumByPointOfSale> sumByPointsOfSaleList = createTargetSumList(operationList, SumByPointOfSale.class);
            sumByPointsOfSaleList.sort(Comparator.comparing(SumByPointOfSale::getPrice).reversed());
            sumByPointsOfSaleList.forEach(s -> saveToTxt(sumsByOfficesWriter, s.toString()));
            return "Calculate sum by point of sales is done";
        } catch (IOException e) {
            System.out.println("Cannot write total price by points of sale into file: ".concat(sumsByOfficesFilename));
        }
        return null;
    }

    /**
     * Create total price list of target class.
     *
     * @see AbstractSumDTO
     *
     * @param operationList list of operation data.
     * @param targetClass   target class.
     * @param <T>           class extended of AbstractSumDTO
     * @return list of target classes.
     */
    private static <T extends AbstractSumDTO> List<T> createTargetSumList(List<OperationDTO> operationList,
                                                                          Class<T> targetClass) {
        List<AbstractSumDTO> targetClassList = new ArrayList<>();
        while (!operationList.isEmpty()) {
            OperationDTO operationDTO = operationList.get(0);
            List<OperationDTO> tempList = operationList.stream()
                    .filter(o -> targetClass.getName().equals(SumByDate.class.getName())
                            ? o.getTime().toLocalDate().equals(operationDTO.getTime().toLocalDate())
                            : o.getPointOfSale().equals(operationDTO.getPointOfSale()))
                    .collect(Collectors.toList());
            if (targetClass.getName().equals(SumByDate.class.getName())) {
                SumByDate sumByDate =
                        new SumByDate(operationDTO.getTime().toLocalDate(), calculateTotalSum(tempList));
                targetClassList.add(sumByDate);
            } else if (targetClass.getName().equals(SumByPointOfSale.class.getName())) {
                SumByPointOfSale sumByPointOfSale =
                        new SumByPointOfSale(operationDTO.getPointOfSale(), calculateTotalSum(tempList));
                targetClassList.add(sumByPointOfSale);
            }
            operationList.removeAll(tempList);
        }
        return (List<T>) targetClassList;
    }

    /**
     * Calculate total sum by operation list.
     *
     * @param operationDTOS operation list data.
     * @return total price.
     */
    private static double calculateTotalSum(List<OperationDTO> operationDTOS) {
        double totalSum = 0;
        for (int i = 0; i <= operationDTOS.size() - 1; i++) {
            totalSum += operationDTOS.get(i).getOperationPrice();
        }
        return Math.floor(totalSum * 100) / 100;
    }

    /**
     * Save data into txt file line by line.
     *
     * @param writer writer.
     * @param data   data for write.
     */
    private static void saveToTxt(FileWriter writer, String data) {
        try {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("Cannot write data file");
        }
    }
}
