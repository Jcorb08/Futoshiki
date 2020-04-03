/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.sussex.ianw.fp.futoshiki.text;

import java.util.Scanner;

/**
 * @author ianw
 *
 * A very simple input line parser that parses each line written by the user to
 * the console. To use, create an object of this type, and then repeatedly call
 * getCommand.
 */
public class Parser {

    private Scanner input;

    public Parser() {
        input = new Scanner(System.in);
    }

    /**
     * Parse the input line, converting the first word encountered into a
     * command, and then passing any further arguments that make sense.
     *
     * @return the parsed command
     */
    public Command getCommand() {
        String inputLine = "";
        inputLine = input.nextLine();

        Scanner scanner = new Scanner(inputLine);
        if (scanner.hasNext()) {
            String str = scanner.next();
            CommandWord cw = CommandWord.getCommandWord(str);
            if (cw == CommandWord.UNKNOWN) {
                return new Command(cw, "Unknown command: " + str);
            } else if (cw == CommandWord.QUIT) {
                return new Command(cw, "Bye bye");
            } else {
                switch (cw) {
                    case NEW:
                        if (scanner.hasNextInt()) {
                            int value = scanner.nextInt();
                            return new Command(cw, value);
                        }
                        break;
                    case CLEAR:
                        if (scanner.hasNextInt()) {
                            int row = scanner.nextInt();
                            if (scanner.hasNextInt()) {
                                int col = scanner.nextInt();
                                return new Command(cw, row, col);
                            }
                        }
                        break;
                    case MARK:
                        if (scanner.hasNextInt()) {
                            int row = scanner.nextInt();
                            if (scanner.hasNextInt()) {
                                int col = scanner.nextInt();
                                if (scanner.hasNextInt()) {
                                    int value = scanner.nextInt();
                                    return new Command(cw, row, col, value);
                                }

                            }
                        }
                        break;
                }
            }

            return new Command(CommandWord.UNKNOWN, cw.getWord() + "  probable insufficient argument");

        }
                  return new Command(CommandWord.UNKNOWN,  "Please tell me what to do");

    }

    

    public static void main(String args[]) {
        Parser p = new Parser();
        Command c = null;
        System.out.print("Enter a command>");
        while ((c = p.getCommand()) != null && c.getCommand() != CommandWord.QUIT) {
            System.out.println(c);
            System.out.print(">");
        }
    }
}
