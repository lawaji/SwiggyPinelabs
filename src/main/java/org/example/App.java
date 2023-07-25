
package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class App {
    List<Location> locationList = new ArrayList<>();
    List<Dish> dishList = new ArrayList<>();
    List<Restro> restroList = new LinkedList<>();

    void parseRestroData() throws IOException{
        BufferedReader inDish = Files.newBufferedReader(Paths.get("C:\\Users\\aakash.lawa\\Downloads\\DEC22-SA\\data\\restro.csv"));
        String line=null;

        Restro tempRestro=null;
        int cntr = 0;
        while( (line=inDish.readLine()) != null)
        {
            String[] restroData = line.split(",");

            tempRestro = new Restro();
            tempRestro.setRestro_id(restroData[0]);
            tempRestro.setRestro_name(restroData[1]);

            tempRestro.setMenu(dishList.stream().filter(dish -> dish.getRestro_id().equals(restroData[0])).collect(Collectors.toList()));

            Optional<Location> tempLocation;
            if( (tempLocation = locationList.stream().filter(location -> location.getRestro_id().equals(restroData[0])).findFirst()).isPresent() )
                tempRestro.setLoc( tempLocation.get());

            restroList.add(cntr,tempRestro);
            cntr++;
        }

    }
    void parseDishData() throws IOException {
        BufferedReader inDish = Files.newBufferedReader(Paths.get("C:\\Users\\aakash.lawa\\Downloads\\DEC22-SA\\data\\dish.csv"));
        String line = null;

        int cntr = 0;
        while ((line = inDish.readLine()) != null) {
            String[] dishData = line.split(",");

            Dish tempDish = new Dish();
            tempDish.setDish_id(dishData[0]);
            tempDish.setRestro_id(dishData[1]);
            tempDish.setDish_name(dishData[2]);
            tempDish.setPrice(Integer.valueOf(dishData[3]));
            dishList.add(cntr, tempDish);

            cntr++;
        }

    }
    void parseLocation() throws IOException {
        BufferedReader inLocation = Files.newBufferedReader(Paths.get("C:\\Users\\aakash.lawa\\Downloads\\DEC22-SA\\data\\location.csv"));
        String line = null;

        int cntr = 0;
        while ((line = inLocation.readLine()) != null) {
            String[] locationData = line.split(",");

            Location tempLocation = new Location();
            tempLocation.setRestro_id(locationData[0]);
            tempLocation.setLatitude(Integer.valueOf(locationData[1]));
            tempLocation.setLongitude(Integer.valueOf(locationData[2]));

            locationList.add(cntr, tempLocation);

            cntr++;
        }

    }
    public static void main(String[] args) {
        //app context
        App app = new App();
        try {
            app.parseLocation();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            app.parseDishData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            app.parseRestroData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Would you like to Browse (1) or Search (2) ?");
        Scanner input1  = new Scanner(System.in);
        int sbchoice = input1.nextInt();

        if(sbchoice == 1)
        {
            app.browse();
        }
        else if (sbchoice == 2)
        {
            app.search();
        }
        else
        {
            System.out.println("Invalid Choice");
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    void browse()
    {
        System.out.println("*********************************************");

        int restroCntr = 0;
        for ( Restro restro: restroList )
        {
            if(restro != null)
            {
                System.out.println((restroCntr+1)+". "+restro.getRestro_name());

                int menuCntr = 0;
                for ( Dish dish : restro.getMenu() )
                {
                    if(dish != null)
                    {
                        System.out.println("   "+(menuCntr+1)+". "+dish.getDish_name()+" INR "+dish.getPrice());
                        menuCntr++;
                    }

                }
                restroCntr++;
                System.out.println("*********************************************");
            }
        }

        createOrder(null);
    }
    void createOrder(List<Restro> orderRestroList)
    {
        if(orderRestroList == null)
        {
            orderRestroList = restroList;
        }

        System.out.print("Please enter the Restro and Dishes in the following format: Restro,Dish1,Qty1,Dish2,Qty2...");
        Scanner orderInput = new Scanner(System.in);
        String orderString  = orderInput.next();
        int orderAmnt = 0;

        String[] orderData = orderString.split(",");
        int restroIndex = Integer.valueOf(orderData[0]);
        List<Dish> menu = orderRestroList.get(restroIndex-1).getMenu();

        Map<Dish,Integer> order = new HashMap<>();

        List<String> orderDataList = new ArrayList<>();

        for (int i = 1; i < orderData.length; i++)
        {
            orderDataList.add(orderData[i]);
        }

        for (int i = 0; i < orderDataList.size(); i++,i++)
        {
            Dish tenpDish = orderRestroList.get(restroIndex-1).getMenu().get(Integer.parseInt(orderDataList.get(i))-1);
            Integer tempQty = Integer.valueOf((orderDataList.get(i+1)));
            order.put(tenpDish,tempQty);
        }

        // for (int i = 1,cntr=0; i < orderData.length; i+=2,cntr++)
        // {
        //orderDishes[cntr] = menu[(Integer.valueOf(orderData[i])-1)];
        //    orderDishes[cntr] = menu.get((Integer.valueOf(orderData[i])-1));
        // }

        // for (int i = 2,cntr=0; i < orderData.length; i+=2,cntr++)
        // {
        //     orderQties[cntr] = Integer.valueOf(orderData[i]);
        //  }

        System.out.println("You chose :"+orderRestroList.get(restroIndex-1).getRestro_name());
        int cntr = 1;
        for (Map.Entry entry : order.entrySet())
        {
            Dish dish = (Dish) entry.getKey();
            Integer qty = (Integer) entry.getValue();

            System.out.println("    "+cntr+". "+dish.getDish_name()+" X "+(Integer)entry.getValue());
            orderAmnt += dish.getPrice()*qty;
            cntr++;
        }

        System.out.println("Total Order Amount: "+orderAmnt);

    }

    void search()
    {
        System.out.println("*********************************************");

        System.out.print("What would you like to Order? :");
        Scanner in = new Scanner(System.in);
        String searchString = in.next();

        StringBuilder patternString = new StringBuilder();

        for (    char c  : searchString.toCharArray()   )
        {
            patternString.append("[");
            patternString.append(  (  String.valueOf(c).toLowerCase() ) );
            patternString.append(  (  String.valueOf(c).toUpperCase() ) );
            patternString.append("]");
        }

        Pattern pattern = Pattern.compile(patternString.toString());

        List<Dish> matchDishes = dishList.stream().filter(dish -> {
            Matcher matcher = pattern.matcher(dish.getDish_name());
            return matcher.find();
        }).collect(Collectors.toList());

        Set<Restro> restroSet = matchDishes.stream().map(dish -> {

            String restroId = dish.getRestro_id();
            Restro theRestro = restroList.stream().filter(restro -> restro.getRestro_id().equals(restroId)).findFirst().get();
            return theRestro;
        }).collect(Collectors.toSet());

        List<Restro> orderRestroList = new ArrayList<>();
        restroSet.forEach(restro -> orderRestroList.add(restro));

        System.out.println("*********************************************");

        int restroCntr = 0;
        for ( Restro restro: orderRestroList )
        {
            if(restro != null)
            {
                System.out.println((restroCntr+1)+". "+restro.getRestro_name());

                int menuCntr = 0;
                for ( Dish dish : restro.getMenu() )
                {
                    if(dish != null)
                    {
                        System.out.println("   "+(menuCntr+1)+". "+dish.getDish_name()+" INR "+dish.getPrice());
                        menuCntr++;
                    }

                }
                restroCntr++;
                System.out.println("*********************************************");
            }
        }

        createOrder(orderRestroList);
    }

}