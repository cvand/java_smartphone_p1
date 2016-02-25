/**
 * @author cvandera
 *
 */

package client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import model.Automobile;

public class CarModelOptionsIO {
	SelectCarOption carOption = new SelectCarOption();

	public CarModelOptionsIO() {

		DefaultSocketClient client = new DefaultSocketClient("127.0.0.1", 4444) {
			@Override
			public void handleSession() {
				// before calling super.handleSession() and starting the loop
				// of listening at the server, we send the first option the user
				// entered
				String welcomeMessage = "Welcome to the Car Configuration System.";
				System.out.println(welcomeMessage);
				String option = null;
				do {
					option = menu();
					if (option.equals("3")) {
						carOption.printOptions();
					}
				} while (option.equals("3"));
				sendOutput(option);

				super.handleSession();
			}

			@SuppressWarnings("unchecked")
			@Override
			public void handleInput(Object obj) {
				if (obj instanceof String) {
					String strInput = (String) obj;

					if (strInput.contains("option1:")) {
						// response after selecting option 1
						String message = strInput.replace("option1:", "");
						Properties props = new Properties();
						boolean noErrors = false;
						do {
							String filename = readInputFromUser(message);

							try {
								FileInputStream in = new FileInputStream(filename);
								props.load(in);
								noErrors = true;
							} catch (IOException e) {
								System.out.println("There is no such file with that name.");
								filename = readInputFromUser(message);
							}
						} while (!noErrors);

						sendOutput(props);
					} else if (strInput.contains("autoSaved")) {
						// automobile saved in the server, return to main menu
						System.out.println("Automobile saved!");
						String option = null;
						do {
							option = menu();
							if (option.equals("3")) {
								carOption.printOptions();
							}
						} while (option.equals("3"));
						sendOutput(option);
					}
				}
				
				if (obj instanceof List) {
					List<String> autos = (ArrayList<String>) obj;
					carOption = new SelectCarOption();
					sendOutput(carOption.selectAutomobileFromList(autos));
				} else if (obj instanceof Automobile) {
					Automobile a = (Automobile) obj;
					System.out.println("Configuration started. (to finish editing, type done)");
					carOption.setAutomobile(a);
					
					String option = null;
					do {
						String setName = readInputFromUser("Enter the option name you want to add:");
						if ("done".equals(setName)) break;
						String choice = readInputFromUser("Enter you choice for this configuration:");
						if ("done".equals(choice)) break;
						carOption.setAutoOption(setName, choice);
					} while (!"done".equals(option));
					
					do {
						option = menu();
						if (option.equals("3")) {
							carOption.printOptions();
						}
					} while (option.equals("3"));
					sendOutput(option);
				}
			}
		};

		client.start();
	}

	private String menu() {
		String option = null;
		String menu = "Please select an option:\nUpload a properties file: press 1\nConfigure a car: press 2\nView configured automobile: press 3";
		boolean validInput = false;
		do {
			option = readInputFromUser(menu);
			if ("1".equals(option) || "2".equals(option) || "3".equals(option)) {
				validInput = true;
			}

		} while (!validInput);
		return option;
	}

	private static String readInputFromUser(String message) {
		System.out.println(message);

		// read user input for a new filename
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
