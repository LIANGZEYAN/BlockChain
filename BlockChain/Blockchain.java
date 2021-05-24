import java.util.*;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;

public class Blockchain{
  java.util.Random random=new java.util.Random();
  // Creat a arraylist of types as Block to store each blcok
  static final List<Block> blockchain = new ArrayList<Block>();
  //indexOfname in name array
  static int indexOfrname = 0;
  // bank array to store every user's balance
  static int[] bank = new int[1000];
  // name array to store every user's name
  static String[] name = new String[1000];
  public static Blockchain fromFile(String fileName){
    // mod as the loop run times.
    int mod = 0;
    //Creat a Sting to read information from txt file.
    String line = null;
    //Creat a new defult Transaction transaction to store sender,receiver and amount from txt file.
    Transaction transaction = new Transaction("","",0);
    //Creat a new Block block to store index,time,hash ,nonce and transaction from txtx file.
    Block block = new Block(0,null,transaction,"","","");
    //Buffer reader part, use buffer reader to read information from the first line to the last line.
    int numberOftimes = 0;
    try {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        while ((line = reader.readLine()) != null) {
          if(mod%7 == 0){
            // use modulo to let block set different data which get from txt fileName
            // For example modulo = 0, index should be set.
            transaction = new Transaction("","",0);
            block = new Block(0,null,transaction,"","","");
            block.setIndex(Integer.parseInt(line));
            mod++;
          }
          else if(mod%7 == 1){
            long l = Long.parseLong(line);
            Timestamp timestampl = new Timestamp(l);
            block.setTimestamp(timestampl);
            mod++;
          }
          else if(mod%7 == 2){
            transaction.setSender(line);
            mod++;
          }
          else if(mod%7 == 3){
            transaction.setReceiver(line);
            mod++;
          }
          else if(mod%7 == 4){
            transaction.setAmount(Integer.parseInt(line));
            mod++;
          }
          else if(mod%7 == 5){
            block.setNonce(line);
            mod++;
          }
          else if(mod%7 == 6){
            block.setHash(line);
            //for every seven steps, transaction will be setted in block.
            block.setTransaction(transaction);
            if(mod == 6){
              //The first block's previoushash is five zeros.
              block.setPreviousHash("00000");
            }
            else{
              block.setPreviousHash(blockchain.get(numberOftimes).getHash());
              numberOftimes++;
            }
            //Add block into blockchain.
            blockchain.add(block);
            mod++;
          }

        }
        reader.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    //These code below is used to debug.
    //int number = (mod+1)/7;
    //for (int i = 0; i < number; i++ ){
      //Block tempB = blockchain.get(i);
      //Transaction tempT = tempB.getTransaction();

      //System.out.println(tempB.getIndex());
      //System.out.println(tempB.getTimestamp());
      //System.out.println(tempT.getSender());
      //System.out.println(tempT.getReceiver());
      //System.out.println(tempT.getAmount());
      //System.out.println(tempB.getNonce());
      //System.out.println(tempB.getHash());
    //}
    return null;
  }

  public static void toFile(String fileName){
    try {
      //Bufferwritter part, use buffer writter to write all information from blockchain into a new txt //file.
			File writename = new File(fileName);
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
      // Use for loop which run serval times as the number of blocks in blockchain to write information //in new new txtx file.
      for(int i =0; i< blockchain.size(); i++){
        out.write(blockchain.get(i).getIndex()+"\r\n");
        out.write(blockchain.get(i).getTimestamp().getTime()+"\r\n");
        out.write(blockchain.get(i).getTransaction().getSender()+"\r\n");
        out.write(blockchain.get(i).getTransaction().getReceiver()+"\r\n");
        out.write(blockchain.get(i).getTransaction().getAmount()+"\r\n");
        out.write(blockchain.get(i).getNonce()+"\r\n");
        out.write(blockchain.get(i).getHash()+"\r\n");
      }
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

  }

  public static boolean validateBlockchain() throws java.io.IOException {
    // Create a flag as the return result.
    boolean flag = true;
    //Create a int indexOfname to store the number names in arrayOfname.
    //The main purpose is avoiding conflict with loop index.
    int indexOfname = 1;
    //For loop to check the sender and add the sender name to a array.
    for(int index = 1; index < blockchain.size(); index++){
      name[0] = blockchain.get(0).getTransaction().getSender();
      if(blockchain.get(index).getTransaction().getSender().equals(name[indexOfname-1])){
        index++;
      }
      else{
        name[indexOfname] = blockchain.get(index).getTransaction().getSender();
        indexOfrname++;
      }
    }
    //For loop to check the receiver and add the receiver name to a array.
    for(int indexx = 0; indexx < blockchain.size(); indexx++){
      for(int ii = 0; ii < name.length; ii++){
        if(blockchain.get(indexx).getTransaction().getReceiver().equals(name[ii])){
          break;
        }
        else{
          if(ii == name.length-1){
            name[indexOfrname] = blockchain.get(indexx).getTransaction().getReceiver();
            indexOfrname++;
          }
          else{

          }
        }
      }
    }
    //Debug
    //System.out.println(name[0]);
    //System.out.println(name[1]);
    //System.out.println(name[2]);
    //System.out.println(name[3]);
    // To calculate the receiver bank account balance.
    for(int indexxx = 0; indexxx < blockchain.size(); indexxx++){
      for(int iii = 0; iii < indexOfrname; iii++){
        if(blockchain.get(indexxx).getTransaction().getReceiver().equals(name[iii])){
          bank[iii] += blockchain.get(indexxx).getTransaction().getAmount();
        }
        else{

        }
      }
    }



    //To calculate the sender bank account balance.
    for(int indexxx = 0; indexxx < blockchain.size(); indexxx++){
      for(int iii = 0; iii < indexOfrname; iii++){
        if(blockchain.get(indexxx).getTransaction().getSender().equals(name[iii])){
          if(blockchain.get(indexxx).getTransaction().getSender().equals("bitcoin")){

          }
          else{
            bank[iii] -= blockchain.get(indexxx).getTransaction().getAmount();
          }
        }
        else{

        }
      }
    }
    //Debug
    //System.out.println(bank[0]);
    //System.out.println(bank[1]);
    //System.out.println(bank[2]);
    //System.out.println(bank[3]);

    //Make sure the sender have enough money to create a new transaction.
    //This is different from the below one, this function is used to check the data in the txt file.
    //The below functiuon is used to check the data in new transaction.
    for(int i = 0; i < indexOfrname; i++){
      if(bank[i] < 0){
        flag = false;
      }
      else{

      }
    }
    //Check the block index.
    for(int i= 0; i < blockchain.size(); i++){
      if(blockchain.get(i).getIndex() != i){
        flag = false;
      }
      else{

      }
    }
    //Check the block previoushash.
    for(int i= 1; i < blockchain.size(); i++){
      if(!blockchain.get(i).getPreviousHash().equals(blockchain.get(i-1).getHash())){
        flag = false;
      }
      else{

      }
    }

    for(int i= 0; i < blockchain.size(); i++){
      //System.out.println(blockchain.get(i).toString());
      //System.out.println(blockchain.get(i).getHash());
      //System.out.println(Sha1.hash(blockchain.get(i).toString()));

      if(!blockchain.get(i).getHash().equals(Sha1.hash(blockchain.get(i).toString()))){
        flag = false;
      }
      else{

      }
    }
    //If any of three requirements is not required, means this block chain is invalid.
    //System.out.println(flag);
    return flag;
  }


  public static int getBalance(String username) throws java.io.IOException {
    //calculate the balance of sender and receiver about a new input transaction.
    //whosbalance is used to find the index of sender's bank in array.
    int whosbalance = 0;
    //Boolean enough is used to check if the sender have enough money to create new transaction.
    boolean enough = true;
    //Input for new receiver and amount of new transaction, the sender input is in main function.
    Scanner in1 = new Scanner(System.in);
    System.out.println("please input a receiver");
    String Receiver = in1.nextLine();
    System.out.println("please input amount");
    int Amount = in1.nextInt();
    //Use for loop to find the sender's bank and check if sender has enough money.
    for(int i = 0; i < indexOfrname; i++){
      System.out.println(name[i]);
      if(username.equals(name[i])){
        //calculate the balance of sender.
          bank[i] -= Amount;
          if(bank[i] < 0){
            enough = false;
            System.out.println("You don't have enough money, transaction will stop");
            return 0;
          }
          else{
            break;
          }

      }
      else{
        //Check the if the sender have a account in bank.
        //If sender don't have bank, which is also mean he don't have any bitcoin.
        if(i == indexOfrname-1 ){
            System.out.println("You don't have account in bank");
            Scanner in7 = new Scanner(System.in);
            System.out.println("please input your name");
            String string = in7.nextLine();
            getBalance(string);
        }
        else{
          // whosbalance is used to find the index of sender's bank in array.
          whosbalance++;
        }



     }
   }

   for(int i = 0; i < indexOfrname; i++){
     if(Receiver.equals(name[i])){
       //calculate the balance of receiver.
       bank[i] += Amount;
       break;
     }
     else{
       if(i == indexOfrname-1 ){
         name[indexOfrname] = Receiver;
         bank[indexOfrname] += Amount;
       }
       else{

       }
     }
   }
    //Sender's current balance.
    System.out.println("This is your current balance");
    System.out.println(bank[whosbalance]);
    //Create the timestamp.
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    //Create default TRANSACTION and Block object to store new transaction's data and information.
    Transaction TRANSACTION = new Transaction("","",0);
    Block BLOCK = new Block(0,null,TRANSACTION,"","","");
    BLOCK.setPreviousHash(blockchain.get(blockchain.size()-1).getHash());
    BLOCK.setIndex(blockchain.size());
    BLOCK.setTimestamp(timestamp);
    TRANSACTION.setSender(username);
    TRANSACTION.setReceiver(Receiver);
    TRANSACTION.setAmount(Amount);
    BLOCK.setTransaction(TRANSACTION);
    //Random the nonce <String>.
    int result =(int)(Math.random() * 19+1);
    String NONCE = "";
    for (int i = 0; i < result; i++){
        char c = (char) (int) (Math.random() * 93 + 33);
        NONCE += c;
    }
    BLOCK.setNonce(NONCE);
    //The nonce and hash code will change until the first five elements are "00000".
    int timeOfrandomtrial = 0;
    while(!Sha1.hash(BLOCK.toString()).substring(0,5).equals("00000")){
      result =(int)(Math.random() * 19+1);
      NONCE = "";
      for (int i = 0; i < result; i++){
          char c = (char) (int) (Math.random() * 93 + 33);
          NONCE += c;
      }
      BLOCK.setNonce(NONCE);
      //System.out.println(BLOCK.toString());
      //System.out.println(Sha1.hash(BLOCK.toString()));
      //System.out.println(BLOCK.getNonce()+"                     ___________");
      timeOfrandomtrial ++;
    }
    //This is used to calculate how many times nonce changed to require the hash code requirement.
    //System.out.println(timeOfrandomtrial);
    String HASH = Sha1.hash(BLOCK.toString());
    //Set the required nonce and hash code.
    BLOCK.setHash(HASH);
    if(enough == false){

    }
    else{
      // Use function add(Block block) to add new block in blockchain.
      add(BLOCK);
    }
    //Return sender's bank account balance.
    return bank[whosbalance];
  }
  public static void add(Block block){
    blockchain.add(block);

  }


  public static void main(String[] args) throws java.io.IOException {
    //Read data from txt file.
    fromFile("blockchain_xwang367.txt");
    //Check the blockchain given by txt file.
    validateBlockchain();
    if(validateBlockchain() == false){
      System.out.println("The blockchain given by txt file is wrong");
      System.exit(0);
    }
    else{
      System.out.println("The blockchain given by txt file is valid");
      //Ask user to create a new transaction.
      Scanner in8 = new Scanner(System.in);
      System.out.println("Do you want to creat a transaction?");
      System.out.println("0 is no , 1 is yes");
      int judge = in8.nextInt();
      if(judge == 1){
        Scanner in3 = new Scanner(System.in);
        System.out.println("please input your name");
        String string = in3.nextLine();
        getBalance(string);
      }
      else{

      }
    }





    Scanner in4 = new Scanner(System.in);
    //Question 5, ask for more transaction and back to question3.
    System.out.println("Do you want to add a new transaction?");
    System.out.println("0 is no , 1 is yes");
    int yesorno = in4.nextInt();
    while(yesorno == 1){
      Scanner in5 = new Scanner(System.in);
      System.out.println("please input your name");
      String string1 = in5.nextLine();
      getBalance(string1);
      Scanner in6 = new Scanner(System.in);
      System.out.println("Do you want to add a new transaction?");
      System.out.println("0 is no , 1 is yes");
      yesorno = in6.nextInt();
    }
    //Question 6, saving the blockchain to a file with specific filename.
    toFile("blockchain_xwang367_zelian068.txt");


  }
}
