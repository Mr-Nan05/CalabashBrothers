# X-Final Project  葫芦娃大战妖精





## 一、小组成员及分工

| 组员姓名 | 学号      | 负责工作                               | 工作量占比 |
| -------- | --------- | -------------------------------------- | ---------- |
| 宋超群   | 171860642 | 网络通信的实现、图形界面的实现         | 50%        |
| 李舟俊潇 | 181860051 | 游戏基本逻辑的设计与编写、图形界面设计 | 50%        |





## 二、项目内容与玩法简介

### 设计思路

​	在本游戏中我们将玩家分别划分为两个阵营，即葫芦娃阵营与妖怪阵营，每个阵营各有七名血量、攻击、防御等数值不同的角色。为了使玩家能够操控己方的每个角色，且整个游戏过程能够有序进行，我们对游戏内容进行了如下设计：

#### 地图

​	我们采取了植物大战僵尸式的网格式地图，每个网格即为各角色移动的基本单位，同时也是攻击、回复判定的基本单位；每个网格最多只能容纳一个角色，只有当角色死亡或从该网格中离开时才允许其他角色进入。

#### 游戏机制

​	在游戏机制上，我们选择了回合制作为游戏进程的基本依托，并借鉴了如精灵宝可梦、纸片马里奥等许多经典的回合制游戏，形成了我们本次游戏的游戏机制：

​	在每回合开始时，两名玩家需要对本回合即将进行的操作进行五步选择；在每步选择中，玩家将选择该步操纵的角色以及该角色需要进行的操作（移动、攻击、开大、回复等）。当五步选择全部完成之后，等待对方玩家选择完成；当双方都选择完成后，由本地根据双方进行的选择，以一定规则依次进行复现，并更新在每步操作中发生变化的各角色坐标、血量、是否已发动大招等属性。当其中有一方全部角色的血量都为0时，另一方玩家所在阵营获得胜利。

​	该机制为本游戏提供了一定策略性，在每回合选择操作的过程中，都需要对对方可能进行的操作进行一个预判，以躲避对方的攻击或打击对方刚移动的角色。

​	另外，上述机制的好处在于，每回合的游戏过程的实体仅仅是双方所选择的操作（字符串），而由双方的操作所引发的游戏内各个角色的行为变化和属性变化，因为双方本地复现游戏过程的逻辑是一致的，故这些行为变化和属性变化实际是与每回合的操作绑定的，是其必然的结果。这就给我们从本地文件复现整个游戏过程的功能的实现提供了可能，我们只需要按回合读取双方操作的字符串，再用与联机游戏相同的复现机制来复现，即可得到与联机游戏相同的游戏过程与展示效果。



### 游戏规则

- 本游戏地图为一个`(5+5)x7`的二维空间战场，葫芦娃和妖精分别只能在左边和右边`5x7`的空间内进行移动，每个网格最多只能容纳一个角色，只有当角色死亡或从该网格中离开时才允许其他角色进入。
- 每方各有七个可操纵角色，葫芦娃阵营可操纵角色为大娃、二娃、三娃、四娃、五娃、六娃、七娃，分别对应编号`1-7`；妖怪阵营可操纵角色为蛇精、蝎子精、鳄鱼头领、金刚大王、小蝴蝶、蜘蛛精、蛤蟆精，分别对应编号`1-7`；双方具有相同编号的角色具有相同的属性值；玩家所操纵的阵营在开始联机时确定。
- 每回合每名玩家一共可进行五步操作的选择，在每步选择中，玩家将选择该步操纵的角色以及该角色需要进行的操作；当双方选择完成之后，将会首先执行先出手玩家的第一步操作，再执行后出手玩家的第一步操作，接着又执行先出手玩家的第二步操作，以此类推；默认第一回合由葫芦娃阵营先出手，每回合结束时调换出手权顺序；若某一步操作进行前，执行该操作的主体角色已阵亡，则跳过该步操作。
- 在每个回合中，可以对某个存活角色进行如下操作：
  - 移动：可用鼠标拖拽该角色到目标网格中；
  - 普通攻击：在选择完操纵的角色后按`Q`键发动普通攻击，该攻击将命中其在该行遇见的第一个敌方角色并造成伤害，伤害值为`(发动攻击角色的攻击值/被击中角色的防御值)x300`；
  - 大招：每名角色最多可使用一次大招，在选择完操纵的角色后按`W`键发动大招，若该角色之前为发动过大招则本次大招发动成功，将会对该角色所在行所有敌方角色造成伤害，伤害值为`(发动攻击角色的攻击值/被击中角色的防御值)x300`；
  - 全体回复：每方阵营最多可使用一次全体回复，在选择完操纵的角色后按`E`键发动全体回复，若本阵营之前为发动过则本次发动成功，将会回复己方所有存活角色血量上限的`50%`血量。



### 游戏流程

1. 在游戏开始界面中点击`开始游戏`，进入到联机界面。

   <img src="D:\日常生活\大三上\JAVA\java20-homework\X-FinalProject\李舟俊潇-181860051-宋超群-171860642\display1.jpg" style="zoom: 50%;" />

2. 在联机界面输入所在局域网的`ip地址`和将要操纵的`阵营(0为葫芦娃阵营，1为妖精阵营)`，若所在局域网中有自己所选择的阵营不同的用户，则建立连接，进入到游戏界面。

   <img src="D:\日常生活\大三上\JAVA\java20-homework\X-FinalProject\李舟俊潇-181860051-宋超群-171860642\display2.jpg" style="zoom:50%;" />

3. 游戏界面中下方为一个`(5+5)x7`的二维空间战场，左上角为己方所有角色的头像和血条，中上方为某一回合己方已选的五个操作的展示及`确认选择`按钮；玩家可通过如下操作进行某一步操作的选择：①直接拖动战场中的角色到其他坐标，中上方即自动出现该角色头像及`移动`操作的图标；②先点选左上角某个角色的头像，此时中上方即出现该角色的头像，再按分别按下键盘上的`Q`、`W`或`E`，中上方即出现`攻击`、`大招`或`回复`的图标；完成选择后，点击`确认选择`按钮即可将本回合选择的操作（字符串）发送给服务器。

   

   ![](D:\日常生活\大三上\JAVA\java20-homework\X-FinalProject\李舟俊潇-181860051-宋超群-171860642\display3.jpg)

   <img src="D:\日常生活\大三上\JAVA\java20-homework\X-FinalProject\李舟俊潇-181860051-宋超群-171860642\display7.jpg" style="zoom: 50%;" /><img src="D:\日常生活\大三上\JAVA\java20-homework\X-FinalProject\李舟俊潇-181860051-宋超群-171860642\display6.jpg" alt="display6" style="zoom:50%;" />

4. 在本地中对双方的操作进行复现，若双方角色没有全部阵亡，则进行下一回合的操作。

   <img src="D:\日常生活\大三上\JAVA\java20-homework\X-FinalProject\李舟俊潇-181860051-宋超群-171860642\display4.jpg" style="zoom: 50%;" /><img src="D:\日常生活\大三上\JAVA\java20-homework\X-FinalProject\李舟俊潇-181860051-宋超群-171860642\display5.jpg" alt="display5" style="zoom:50%;" />

   <img src="D:\日常生活\大三上\JAVA\java20-homework\X-FinalProject\李舟俊潇-181860051-宋超群-171860642\display9.jpg" style="zoom:33%;" /><img src="D:\日常生活\大三上\JAVA\java20-homework\X-FinalProject\李舟俊潇-181860051-宋超群-171860642\display8.jpg" alt="display9" style="zoom:33%;" />

   <img src="D:\日常生活\大三上\JAVA\java20-homework\X-FinalProject\李舟俊潇-181860051-宋超群-171860642\display10.jpg" style="zoom: 50%;" /><img src="D:\日常生活\大三上\JAVA\java20-homework\X-FinalProject\李舟俊潇-181860051-宋超群-171860642\display11.jpg" alt="display11" style="zoom: 50%;" />





## 三、代码模块介绍

​	本实验大致分为两个模块：

- `Server`模块：主要负责运行网络服务器，进行数据报的转发；
- `Client`模块：负责游戏的主要运行逻辑。



### Server模块

​	此模块中有一个程序的入口`main`函数，主要负责转发对战双方需要发送的数据报，为了降低逻辑复杂度，将服务器的逻辑设置为集线器`hub`，即将接受到的数据报向每一个用户进行转发。

#### TCP连接

​	首先服务器与客户端通过TCP端口进行连接, 并把自己的UDP端口号发送给对方，之后服务器将所有通过建立连接收到的UDP端口号以及IP地址存储在ArrayList里面,在需要的时候遍历该列表一次进行转发数据报。

​	这里由于建立TCP连接的过程与接收、转发UDP数据报的过程均需要一直占用所在的线程，故将UDP接收与转发相关的功能放在一个单独的线程中进行实现。

- TCP建立连接

  ```java
  //给客户配专属TCP套接字
      void TCPConnect(){
          Socket TCPSocket = null;
          int clientUDPPort = 0;
          try {
              TCPSocket = serverSocket.accept();//给客户但分配专属TCP套接字
              System.out.println("A client has connected...");
  
              DataInputStream dis = new DataInputStream(TCPSocket.getInputStream());
              clientUDPPort = dis.readInt();//记录客户端UDP端口
              String clientIp = TCPSocket.getInetAddress().getHostAddress();
              System.out.println(clientIp);
              ObjClient client = new ObjClient(clientIp, clientUDPPort);//创建Client对象
              clients.add(client);//添加进客户端容器
  
              DataOutputStream dos = new DataOutputStream(TCPSocket.getOutputStream());
              dos.writeInt(UDP_PORT);//告诉客户端自己的UDP端口号
  
          }catch (IOException e) {
              e.printStackTrace();
          }finally {
              try {
                  if(TCPSocket != null) TCPSocket.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
  ```

- 数据报的接收与转发

  ```java
          //创建发送数据的UDP Socket
          setDataSocket();
  
          while (dataSocket != null){
              try {
                  //接收数据到dataPacket
                  dataReceive();
                  //服务器向每一个目标元素广播
                  dataSend();
                  TimeUnit.MILLISECONDS.sleep(1000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
  ```

  

### Client模块

​	`Client`模块可以细分为生成所有游戏对象的`Creature`模块、负责游戏逻辑的`World`模块、负责图形界面的`View模块`以及负责发送数据报的`Protocol`模块。

#### Creature模块

​	本模块维护了所有角色所共有的属性和行为，包括id、血量、攻击值、防御值、坐标、是否发动大招的标志以及该角色对应的图片等；再分别由`Brother`类和`Demon`类继承，分别设定了葫芦娃和妖精的初始坐标；接着再由葫芦娃和妖精各七个子类分别继承`Brother`类和`Demon`类，分别代表每方七个角色，并在其中设定每个角色的具体属性值。

```java
public class Creature {
    protected int id;//生物id
    protected String name;//生物名
    protected int hp;//血量
    protected int resthp;//剩余血量
    protected int power;//攻击力
    protected int defense;//防御力
    protected boolean specialattackflag;//是否发动过大招
    protected boolean alive;//是否存活
    protected DraggableView imageView;
    protected int X;//横坐标
    protected int Y;//纵坐标
	...
}

public class Brother extends Creature{
    ...
}
public class RedBro extends Brother {
    ...
}
public class Demon extends Creature{
	...
}
public class SnakeDemon extends Demon {
    ...
}
```



#### World模块

​	整个`Client`模块的入口就行`World`模块中的`Main`类中，大致过程是从`Main`进入程序运行，在`Main`中先试用`View`模块中的程序将构造出所有需要的页面（如启动页面、连接页面、游戏页面、结束页面），并根据界面之间的联系，在合适的时机显示合适的页面，此外游戏的逻辑主要在`World`类中实现，并在`Lock`类中控制处理各角色之间的冲突。

##### Main类

​	作为整个程序的入口，在此处初始化所有主要对象，并由此处进入游戏界面。

##### World类

​	由于本类是复现每回合游戏过程的基本类，所以需要在本类中维护游戏中双方所有角色的状态，攻击、移动、回复等行为在底层逻辑上的实现及状态更新，以及出手权、发动回复的标志、回合数和暂存某回合操作等各状态变量等。

```java
	private boolean SIDE;//阵营，0表示葫芦娃，1表示妖精
    public static int NUM=7;//每方角色总数
    private boolean[] clife={true,true,true,true,true,true,true};//己方每个角色的存活状态
    private int myalive=7;//己方剩余角色数
    private int opalive=7;//对方剩余角色数
    private boolean myrecoverflag=true;//己方是否可以群体回复
    private boolean oprecoverflag=true;//对方是否可以群体回复

    private Map<Integer, Creature> BROS;//己方角色
    private Map<Integer, Creature> DEMONS;//对方角色
    private Map<Integer, Creature> SELF;//己方角色
    private Map<Integer, Creature> OPPO;//对方角色

    private Type type;//操纵的是葫芦娃还是妖怪
    private Order order;//出手顺序
    public int round;//当前回合数

    String mychoice;//某回合己方的操作
    String opchoice;//某回合对方的操作

    String time;//文件名中的时间
    String filename;//文件名
```

​	`transList2String(List<String> act)`方法和`renewBattleMessage(String command)`是本模块的主要方法，前者负责接收从图形界面传来的存有己方本回合操作的List，并将其转化为可被阅读和解析的字符串，将其发送至服务器并保存在txt文件（以开始游戏的时间戳命名，如`2020-12-29 12-11-57.txt`）中；后者负责从服务器接收存有己方或对方某一步操作的字符串（若为对方的操作，则需要将其写入txt文件中），通过解析该字符串获得需要操纵的角色以及该角色进行的操作（移动、攻击、开大、回复等），再调用相应的函数进行坐标、血量等属性的更新。

```java
public void transList2String(List<String> act);

public int renewBattleMessage(String command);

private void creatureMove(int id,int x,int y);

private void creatureAttack(int id);
private Map<Integer, Creature> bulletAction(int damage,int x,int y,Dir dir,Map<Integer, Creature> hit);
private Map<Integer, Creature> bulletHit(Integer key,Map<Integer, Creature> hit,int damage);

private void creatureSpecialAttack(int id);
private Map<Integer, Creature> specialAttackWork(int damage,int y,Map<Integer, Creature> hit);

private void creatureRecovery(int id);
private Map<Integer, Creature> recovery(Map<Integer, Creature> hit);
```

##### Lock类

​	主要用来处理各角色之间的位置冲突，使用int型二维数组存储各个角色的位置，在图形界面上将角色移动之后会在此处将角色的逻辑位置进行移动。

- 位置存储

  ```java
  private int [][] MARK = new int[10][7];
  ```

- 移动与冲突控制

  ```java
  //如果移动的目标位置是空闲的就成功占用并返回True，否则拒绝占用并返回False
  public boolean occupy(int id, int srcX, int srcY, int desX, int desY){
      if(LOCK)
          return false;
      else if(MARK[desX - 1][desY - 1] == 0){
          MARK[desX - 1][desY - 1] = id;
          MARK[srcX - 1][srcY - 1] = 0;
          return true;
      }
      else return false;
  }
  //移动成功之后将原本占用的位置释放
  void release(int x, int y){
      MARK[x - 1][y - 1] = 0;
  }
  //控制角色进行移动
  public boolean move(int id, int srcX, int srcY, int desX, int desY){
      if(LOCK)
          return false;
      else if(occupy(id, desX, desY)) {
          release(srcX, srcY);
          Main.gameStage.setMove(abs(id), desX, desY);
          Main.gameStage.addBackAction(new int[]{abs(id), srcX, srcY, desX, desY});
          return true;
      }
      else return false;
  }
  ```



#### View模块

​	本模块负责图形界面的控制，包括各个界面的构建及联系、各种操作的图形显示等等，各个界面都继承于同一个父类`BaseStage`，主要控制一些界面间共有的基础功能。

##### DraggableView类

​	继承自`ImageView`类，每一个角色对应的图形对象都是`DraggableView`，即可以拖动的视图，用来让用户使用鼠标控制角色移动，通过直接拖动或者点击并在目标位置再次点击来使角色移动。

##### 攻击与技能相关类

​	主要包括普通攻击`BulletAttack`类、大招`SpecialAttack`类、回复技能`Recovery`类，为了不阻塞当前进程，每一个攻击或者技能对象都在一个单独的线程中进行，并根据操作方的阵营来读取显示对应的图像。

##### StartStage类

​	从Main中首先进入此界面中，通过用户选择开始游戏或者读取本地回放进入对应的游戏逻辑中，如果选择开始游戏就会进入`ConnectStage`也就是连接界面中。

##### ConnectStage类

​	通过用户输入进行连接的IP地址以及阵营的选择（0表示葫芦娃，1表示妖精），进入到游戏界面中，并将此界面隐藏。

##### GameStage类

​	游戏界面，拖动角色移动或者点击角色头像的时候在右上方对应的空缺位置显示该角色头像与将要进行的操作，操作满了之后点击对勾，所有的操作都会空缺出来，角色也会移动到原来的位置，并将所有的操作通过字符串形式发到服务器，并由服务器进行转发，在收到服务器转发的信息之后将收到的每一步信息复现出来，通过对应的逻辑控制，双方的每一步操作交替复现，并进行扣血与输赢判断，当由一方获胜之后会唤起结束界面，并将本界面隐藏。

- 对应的存储结构

  ```java
  List<Button> chooseButtons;//选择头像的button
  List<Button> cancelButtons;//取消选择的button
  List<ChosenView> chosenViews;//放置被选择的头像图片
  List<ChosenView> actionViews;//放置动作图片
  List<Image> headImages;//所有的头像Image
  List<Image> actionImages;//所有动作的Image
  List<String> actionMessages;//记录所有的信息，每条消息是4~5个数字，分别代表id序号、位置、执行动作(
                              // 0-3分别代表大招、普攻、恢复、移动)
  ```

- 生成字符串的格式如下

  ```
  BROTHER FIRST ; 7 MOVE 3 7 ; 6 MOVE 2 7 ; 4 MOVE 8 4 ; 3 MOVE 8 3 ; 6 MOVE 9 5 ;
  ```



#### Protocol模块

​	本模块负责客户端的网络服务，包括与服务器建立连接、数据的打包与转发等。

##### Packet类

​	此部分主要负责数据的打包，将每回合用户的操作生成的字符串与目标对象的地址与端口打包起来。

##### NetClient类

​	此部分主要负责建立连接与数据转发，根据用户的阵营选择对应的UDP端口，使用一个单独的线程进行持续接收数据，在收到服务器转发的数据之后将收到的字符串解析并在对应的时机控制角色进行对应的操作。



### 运行方式

```
java -cp CalabashBrothers-1.0.jar com.cbsl.app.server.NetServer
java -cp CalabashBrothers-1.0.jar com.cbsl.app.client.world.Main
```





## 四、设计模式知识总结

#### 目标

让程序具有更好的代码重用性、可读性、可扩展性、可靠性以及使程序呈现高内聚，低耦合的特性。



#### 原则

**1、单一职责原则（SRP，The Single Responsibility Principle）**

​	一个类，只有一个引起它变化的原因。应该只有一个职责。
**2、开放封闭原则（OCP，The Open Closed Principle）**

​	开闭原则就是说对扩展开放，对修改关闭。
**3、里氏替换原则（LSP，The Liskov Substitution Principle）**

​	里氏代换原则(Liskov Substitution Principle LSP)面向对象设计的基本原则之一。 里氏代换原则中说，任何基类可以出现的地方，子类一定可以出现。

**4、依赖倒置原则（DIP，The Dependency Inversion Principle）**

​	依赖倒置原则的核心思想是面向接口编程，不应该面向实现类编程：

1. 低层模块尽量都要有抽象类或接口，或者两者都有。
2. 抽象不应该依赖细节，细节应该依赖抽象；
3. 变量的声明类型尽量是抽象类或接口；
4. 使用继承时遵循里氏替换原则；
5. 使用接口或抽象的目的是制定规范，而不涉及任何具体的操作，把展现细节的任务交给实现类去完成。


**5、接口分离原则（ISP，The Interface Segregation Principle）**

​	接口隔离原则的含义是：建立单一接口，不要建立庞大臃肿的接口，尽量细化接口，接口中的方法尽量少。
**6、迪米特法则（LoD，The Law of Demeter、LKP）**

​	最少知道原则，一个实体应当尽量少的与其他实体之间发生相互作用，使得系统功能模块相对独立。

**7、合成复用原则**

​	当要扩展类的功能时，优先考虑使用组合，而不是继承。



#### 分类

**创建型模式**：工厂方法模式、抽象工厂模式、单例模式、建造者模式、原型模式。

**结构型模式**：适配器模式、装饰器模式、代理模式、外观模式、桥接模式、组合模式、享元模式。

**行为型模式**：策略模式、模板方法模式、观察者模式、迭代子模式、责任链模式、命令模式、备忘录模式、状态模式、访问者模式、中介者模式、解释器模式。





## 五、感想与遗憾

​	本次实验是对整个学期java语言学习的综合运用，在完成本次项目的过程中，我们体会到了从设计游戏逻辑，到设计代码结构并实现的这个过程中一系列酸甜苦辣，收获良多；但是因为我们分工对接不及时、时间规划不周，导致最终未能完成debug工作、实现完整的游戏过程以及本地回放功能，这是本次实验最大的遗憾。

在代码编写的过程中，遇到了很多自己最初无法解决的问题，后来复习了前面的知识并在网上找了很多相关的内容才得以解决。

- 服务器与客户端需要做死循环持续接收数据报会阻塞进程

  解决方法：将二者放在单独的线程中进行

- 客户端接收到服务器转发的字符串解析后并调用相关的操作函数，但是由于二者处于不同的线程，所有操作会在同一时间进行刷新，导致产生各种异常。

  解决方法：将字符串解析后存储到World中，由主程序进行调度

