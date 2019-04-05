package cn.v;/**
 * Created by VLoye on 2019/4/4.
 */



import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author V
 * @Classname Console
 * @Description
 **/
public class Console {


    public static void main(String[] args) {
        System.out.println("Welcome to use my-platform console");
        System.out.println("Current version : 1.0");
        System.out.println("You can view [help] by typing 'help'");
        System.out.print("my-platform command : ");

        CommandExecutor executor = new CommandExecutor();

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        while (!command.equals(CommandConstant.EXIT)) {
            String[] coms = command.split(" ");
            if (coms.length > 0) {
                executor.execute(coms);
            } else {
                System.out.println("The syntax of the command is incorrect. Please retry");
            }
            System.out.print("my-platform command : ");
            command = scanner.nextLine();
        }
        System.out.println("Command exit!");
    }



}
