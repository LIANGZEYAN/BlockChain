public class Block {
  private int index; // the index of the block in the list
  private java.sql.Timestamp timestamp; // time at which transaction has been processed
  private Transaction transaction; // the transaction object
  private String nonce; // random string (for proof of work)
  private String previousHash; // previous hash (set to "" in first block)
  private String hash; // hash of the block (hash of string obtained from previous variables via toString() method)


////Constructor.
  public Block(int Index, java.sql.Timestamp timestamp,Transaction transaction,String nonce,String previousHash,String hash){
    this.index = index;
    this.timestamp = timestamp;
    this.transaction = transaction;
    this.nonce = nonce;
    this.previousHash = previousHash;
    this.hash = hash;
  }
////Getter and Setter part.
  public int getIndex(){
    return this.index;
  }

  public java.sql.Timestamp getTimestamp(){
    return this.timestamp;
  }

  public Transaction getTransaction(){
    return this.transaction;
  }

  public String getNonce(){
    return this.nonce;
  }

  public String getPreviousHash(){
    return this.previousHash;
  }

  public String getHash(){
    return this.hash;
  }

  public void setIndex(int index){
    this.index = index;
  }

  public void setTimestamp(java.sql.Timestamp timestamp){
    this.timestamp = timestamp;
  }

  public void setTransaction(Transaction transaction){
    this.transaction = transaction;
  }

  public void setNonce(String nonce){
    this.nonce = nonce;
  }

  public void setPreviousHash(String previousHash){
    this.previousHash = previousHash;
  }

  public void setHash(String hash){
    this.hash = hash;
  }



  public String toString() {
    return timestamp.toString() + ":" + transaction.toString() + "." + nonce + previousHash;
  }
}
