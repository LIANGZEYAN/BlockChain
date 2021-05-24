public class Transaction {
  private String sender;// The sender of transaction.
  private String receiver;// The receiver of transaction.
  private int amount;//The amont of transaction.


//Constructor
  public Transaction(String sender,String receiver,int amount){
    this.sender = sender;
    this.receiver = receiver;
    this.amount = amount;
  }
//Getter and Setter part
  public String getSender(){
    return this.sender;
  }

  public String getReceiver(){
    return this.receiver;
  }

  public int getAmount(){
    return this.amount;
  }

  public void setSender(String sender){
    this.sender = sender;
  }

  public void setReceiver(String receiver){
    this.receiver = receiver;
  }

  public void setAmount(int amount){
    this.amount = amount;
  }


  public String toString() {
  return sender + ":" + receiver + "=" + amount;
  }
}
