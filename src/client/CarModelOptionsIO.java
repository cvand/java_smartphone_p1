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
						System.out.println("No configuration found.");
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
						System.out.println("Automobile saved!");
						String option = null;
						do {
							option = menu();
							if (option.equals("3")) {
								System.out.println("No configuration found.");
							}
						} while (option.equals("3"));
						sendOutput(option);
					}
				}
				
				if (obj instanceof List) {
					List<String> autos = (ArrayList<String>) obj;
					System.out.println("The list of available automobiles is:");
					for (String a : autos) {
						System.out.println(a);
					}
					String modelName = readInputFromUser("Please select one automobile by entering its name:");
					String m = "modelName:" + modelName;
					sendOutput(m);
				} else if (obj instanceof Automobile) {
					Automobile a = (Automobile) obj;
					System.out.println("Configuration started. (to finish editing, type done)");
					SelectCarOption carOption = new SelectCarOption(a);
					
					String option = null;
					do {
						String setName = readInputFromUser("Enter the option name you want to add:");
						if ("done".equals(setName)) break;
						String choice = readInputFromUser("Enter you choice for this configuration:");
						if ("done".equals(choice)) break;
						carOption.setAutoOption(setName, choice);
					} while (!"done".equals(option));
//					carOption.setAutoOption("Color", "Infra-Red Clearcoat");
//					carOption.setAutoOption("Side Impact Air Bags", "Not present");
//					carOption.setAutoOption("Power Moonroof", "Present");
//					carOption.setAutoOption("Brakes/Traction Control", "ABS");
//					carOption.setAutoOption("Transmission", "Standard");
					
					
					option = null;
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
