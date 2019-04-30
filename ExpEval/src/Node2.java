
//Node2.java

public class Node2<T>
{
  private T data;
  private Node2 link;

  //constructor
  public Node2(T newData, Node2 linkValue)
  {
    data = newData;
    link = linkValue;
  }

  public T getData()
  {
    return data;
  }

  public Node2 getLink()
  {
    return link;
  }

  public void setData(T y)
  {
    data = y;
  }

  public void setLink(Node2 linkValue)
  {
    link = linkValue;
  }

}
