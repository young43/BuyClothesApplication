# BuyClothes

국민대학교 모바일프로그래밍 첫번째 개인과제에 대한 프로젝트입니다.

20191633 윤서영

Github: https://github.com/young43/BuyClothesApplication

<br>

## 목차

- [1. 프로젝트 설명](#1-프로젝트-설명)
  - [1.1 프로젝트 개요](#11-프로젝트-개요)
  - [1.2 소스파일 구성](#12-소스파일-구성)
  - [1.3 개발환경](#13-개발환경)
  - [1.4 실행환경](#14-실행환경)
  
- [2. UI 설계](#2-UI-설계)
  - [2.1 첫번째 화면(Main)](#21-첫번째-화면main)
  - [2.2 두번째 화면(장바구니)](#22-두번째-화면장바구니)
  - [2.3 세번째 화면(구매)](#23-세번째-화면구매)

- [3. 구현내용](#3-구현내용)
  - [3.1 Activity](#31-Activity)
  - [3.2 Fragment](#32-Fragment)
  - [3.3 Custom Dialog](#33-CustomDialog)
  - [3.4 Custom ListView](#34-Custom-ListView)

<br>

## 1. 프로젝트 설명

### 1.1 프로젝트 개요

2개 이상의 상품을 화면에 출력하고, 상품을 선택하면 장바구니 또는 구매화면으로 이동할 수 있게 한다. 장바구니 화면에서는 여러 상품을 체크하여 구매화면으로 이동할 수 있다. 마지막 구매화면에서는 선택한 제품명과 가격 정보를 출력하고, 주소 및 연락처를 입력할 수 있다. 최종적으로 구매 완료시, 다시 상품선택 화면으로 이동하게 된다.

### 1.2 소스파일 구성

| 파일 명                 | 역할                                                         |
| ----------------------- | ------------------------------------------------------------ |
| MainActivity.java       | 가장 처음으로 실행되는 메인 액티비티이다.                    |
| ProductFragment.java    | 첫번째 화면(상품선택 화면)에 대한 프래그먼트이다.            |
| CartFragment.java       | 두번째 화면(장바구니 화면)에 대한 프래그먼트이다.            |
| BuyFragment.java        | 세번째 화면(구매 화면)에 대한 프래그먼트이다.                |
| CustomDialog.java       | 상품을 클릭했을 때, 구매/장바구니 버튼을 보여주는 커스텀 다이얼로그이다. |
| ListViewItem.java       | 장바구니에 담긴 상품 리스트에 대한 아이템 클래스이다.        |
| ListViewAdapter.java    | 장바구니에 담긴 상품 리스트를 보여주기위한 어댑터이다.       |
| BuyListViewItem.java    | 구매할 상품 리스트에 대한 아이템 클래스이다.                 |
| BuyListViewAdapter.java | 구매할 상품 리스트를 보여주기위한 어댑터이다.                |

### 1.3 개발환경

- Language: Java
- IDE: Android Studio

### 1.4 실행환경

- AVD: Android Q(10.0 x86_64)
- Compile
  - minSDKVersion: 16
  - compileSDKVersion: 30

<br>

## 2. UI 설계

| 레이아웃 명           | 연결               | 역할                                                 |
| --------------------- | ------------------ | ---------------------------------------------------- |
| activity_main.xml     | MainActivity       | 메인 액티비티에 연결되는 레이아웃이다.               |
| fragment_product.xml  | ProductFragment    | 첫번째 화면(상품 리스트) 레이아웃이다.               |
| fragment_cart.xml     | CartFragment       | 두번째 화면(장바구니) 레이아웃이다.                  |
| fragment_buy.xml      | BuyFragment        | 세번째 화면(구매) 레이아웃이다.                      |
| custom_dialog.xml     | CustomDialog       | 사용자정의 다이얼로그에 대한 레이아웃이다.           |
| listview_item.xml     | ListViewAdapter    | 장바구니 화면의 리스트뷰에 대한 아이템 레이아웃이다. |
| listview_item_buy.xml | BuyListViewAdapter | 구매 화면의 리스트뷰에 대한 아이템 레이아웃이다.     |

### 2.1 첫번째 화면(Main)

첫번째 화면은 2개 이상의 상품 리스트를 보여주고, 선택하면 커스텀 다이얼로그를 띄우게 된다. 

커스텀 다이얼로그에는 구매 버튼과 장바구니 버튼이 존재한다. 

<img src="https://github.com/young43/BuyClothesApplication/blob/main/img/screenshot1.png" alt="첫화면 스크린샷" width=250/>

### 2.1.1 레이아웃

Main 액티비티의 레이아웃은 _activity_main.xml_ 파일에 구성되어있다.  또한, 최상위 부모 View는 ConstraintLayout이며, 자식은 BottomNavigationView와 FrameLayout 으로 구성되어있다. 

```xml
<com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottomNavi"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:itemBackground="@color/white"
        app:itemIconTint="@drawable/tab_color"
        app:itemTextColor="@drawable/tab_color"
        app:layout_constraintBottom_toBottomOf="parent"
        app:menu="@menu/main_menu"
        tools:layout_editor_absoluteX="16dp" />

    <FrameLayout
        android:id="@+id/Main_Frame"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintBottom_toTopOf="@+id/bottomNavi"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"/>
```

### 2.1.2 ProductFragment 레이아웃

Main 액티비티는 사실상 하단 네비게이션바와 FramLayout 으로만 구성이 되어있다. 따라서, 각 Fragment 들은 또 다른 레이아웃들로 구성하여 FramLayout에 연결되는 구조로 되어있다.

상품 리스트 화면 레이아웃은 _fragment_product.xml_ 파일에 구성되어있다.

최상위 부모 View는 RelativeLayout으로 설정하였고, LinearLayout과 ScrollView를 사용하여 상품들을 스크롤 하면서 볼 수 있도록 하였다. 각 상품에 대한 정보(가격, 이미지 등)들은 RelativeLayout 안에 생성하였고, 필요시 id를 할당해 주었다.

```xml
<RelativeLayout
    android:layout_width="match_parent"
    android:layout_height="180dp"
    android:layout_marginTop="20dp"
    android:background="@drawable/background_border"
    android:elevation="10dp"
    android:id="@+id/layout_product1">

    <ImageView
        android:layout_width="145dp"
        android:layout_height="145dp"
        android:layout_centerVertical="true"
        android:layout_marginLeft="25dp"
        android:src="@drawable/product1"
        android:background="#FFECE0"
        android:scaleType="fitXY"
        android:padding="5dp"
        android:id="@+id/imgv_product1"/>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginRight="25dp"
        android:layout_alignTop="@+id/imgv_product1"
        android:layout_toRightOf="@+id/imgv_product1"
        android:orientation="vertical">
        
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:textSize="7.5pt"
            android:text="키르시 맨투맨"
            android:id="@+id/textv_product_title1"/>

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="30dp"
            android:gravity="center"
            android:textSize="7pt"
            android:textStyle="italic"
            android:text="56000원"
            android:id="@+id/textv_product_price1"/>
    </LinearLayout>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignBottom="@id/imgv_product1"
        android:layout_alignParentRight="true"
        android:layout_marginRight="25dp"
        android:text="별점: ★★★★☆"/>
</RelativeLayout>
```

### 2.2 두번째 화면(장바구니)

장바구니 화면은 장바구니에 추가한 상품들에 대한 리스트를 출력해준다. 체크박스를 사용하여 상품을 선택할 수 있고, 장바구니에서 삭제할 수도 있다. 

<img src="https://github.com/young43/BuyClothesApplication/blob/main/img/screenshot3.png" alt="두번째 화면" width=250/>

### 2.2.1 레이아웃

상단에는 전체선택 체크박스가 존재하고, 클릭하면 각 상품들이 전부 체크된다. 또한 삭제버튼과 구매하기 버튼이 존재한다. 최상위 부모 View는 LinearLayout으로 되어있으며, 장바구니 리스트는 ListView와 ScrollView를 활용하여 보여주도록 하였다. 

장바구니 화면 레이아웃은 _fragment_cart.xml_ 파일에 구성되어있다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".CartFragment"
    android:orientation="vertical">
    
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:gravity="center_horizontal|center_vertical"
        android:id="@+id/textv_title2"
        android:text="@string/title2"
        android:textSize="8pt"
        android:textStyle="bold|italic"
        android:paddingTop="10dp"
        android:paddingBottom="10dp"
        android:background="#FFECCE"
        android:elevation="10dp"/>

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:orientation="horizontal"
        android:layout_marginTop="5dp"
        android:layout_marginBottom="5dp"
        android:layout_marginLeft="5dp">
        
        <CheckBox
            android:id="@+id/checkbox_all"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:gravity="center"
            android:backgroundTint="#2196F3" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:text="전체선택"
            android:gravity="center_vertical"
            android:textSize="16dp"
            android:textColor="#000000"
            android:layout_toRightOf="@id/checkbox_all"/>

        <Button
            android:id="@+id/button_delete"
            android:layout_width="80dp"
            android:layout_height="40dp"
            android:layout_alignParentRight="true"
            android:layout_centerVertical="true"
            android:layout_marginRight="10dp"
            android:background="#2196F3"
            android:text="삭제"
            android:textColor="#FFFFFF"/>
    </RelativeLayout>

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_weight="1">
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            <ListView
                android:id="@+id/list_cart"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:choiceMode="multipleChoice"/>
        </LinearLayout>
    </ScrollView>

    <Button
        android:id="@+id/button_purchase"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="구매하기"
        android:textSize="16dp"
        android:textStyle="bold"
        android:background="#FF9800"
        android:textColor="#FFFFFF"/>
</LinearLayout>
```

### 2.2.2 ListViewItem 레이아웃

장바구니 화면의 리스트는 각 item에 이미지를 포함하기 위해서 item에 대한 레이아웃을 따로 정의하였다.  RelativeLayout안에 Checkbox, ImageView, TextView를 통해 상품 정보를 보여주고자 하였다. 

장바구니 리스트 ltem 레이아웃은 _listview_item.xml_ 파일에 구성되어있다.

```xml
<RelativeLayout
android:layout_width="match_parent"
android:layout_height="170dp"
android:elevation="10dp">

<CheckBox
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:id="@+id/checkbox1"
    android:layout_centerVertical="true" />

<ImageView
    android:layout_width="145dp"
    android:layout_height="145dp"
    android:layout_marginLeft="5dp"
    android:layout_centerVertical="true"
    android:src="@drawable/product1"
    android:background="#FFECE0"
    android:scaleType="fitXY"
    android:padding="5dp"
    android:id="@+id/imageView1"
    android:layout_toRightOf="@id/checkbox1" />

<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginRight="25dp"
    android:layout_alignTop="@+id/imageView1"
    android:layout_toRightOf="@+id/imageView1"
    android:orientation="vertical">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:textSize="7.5pt"
        android:text="키르시 맨투맨"
        android:id="@+id/textView1" />
</LinearLayout>

<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:textSize="7pt"
    android:textStyle="italic"
    android:text="56000원"
    android:id="@+id/textView2"
    android:layout_alignBottom="@id/imageView1"
    android:layout_alignParentRight="true"
    android:layout_marginRight="30dp" />
</RelativeLayout>
```

### 2.3 세번째 화면(구매)

구매 화면에서는 결제를 진행할 상품 리스트와 결제 총합을 출력한다. 선택한 상품이 여러개일 경우, 총합으로 계산하여 보여주고 수량을 수정할 수 있다. 구매자의 주소와 연락처를 입력해야만 결제를 진행할 수 있다. 최종적으로 결제가 완료되면, 다시 첫 화면(상품 선택 화면)으로 돌아간다.

<img src="https://github.com/young43/BuyClothesApplication/blob/main/img/screenshot4.png" alt="세번째 화면" width=250/>

<img src="https://github.com/young43/BuyClothesApplication/blob/main/img/screenshot5.png" alt="최종결제화면" width=250/>

### 2.3.1 레이아웃

최상위 부모 View는 GridLayout으로 구성하였다. 상단에는 주소와 연락처를 입력받을 수 있도록 EditText를 사용하였고, 장바구니 화면과 마찬가지로 구매할 상품 리스트를 보여주기위해 ListView와 ScrollView를 사용하였다. 하단에는 결제하기 버튼이 존재한다. 

구매 화면 레이아웃은 _fragment_buy.xml_ 파일에 구성되어있다.

```xml
<GridLayout 	xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".BuyFragment"
    android:columnCount="3"
    android:rowCount="10">

    <TextView
        android:layout_row="0"
        android:layout_column="0"
        android:layout_columnSpan="3"
        android:id="@+id/textv_title3"
        android:text="@string/title3"
        android:textSize="8pt"/>

    <TextView
        android:layout_row="1"
        android:layout_column="0"
        android:text="주소 입력: "
        android:textSize="14dp"/>

    <EditText
        android:id="@+id/edit_address"
        android:layout_row="1"
        android:layout_column="1"
        android:layout_columnSpan="2"
        android:hint="주소를 입력해주세요."
        android:textSize="12dp"/>

    <TextView
        android:layout_row="2"
        android:layout_column="0"
        android:text="번호 입력: "
        android:textSize="14dp"/>

    <EditText
        android:id="@+id/edit_phoneNumber"
        android:layout_row="2"
        android:layout_column="1"
        android:layout_columnSpan="2"
        android:hint="번호를 입력해주세요."
        android:textSize="12dp"/>

    <ScrollView
        android:layout_row="3"
        android:layout_column="0"
        android:layout_columnSpan="3"
        android:layout_rowSpan="5">
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            <ListView
                android:id="@+id/list_buy"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"/>
        </LinearLayout>
    </ScrollView>

    <LinearLayout
        android:layout_row="8"
        android:layout_column="0"
        android:layout_columnSpan="3"
        android:orientation="horizontal"
        android:gravity="right">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:text="총 결제 금액: "
            android:textSize="17dp"/>

        <TextView
            android:id="@+id/textv_total_price"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:textSize="17dp"/>
    </LinearLayout>

    <Button
        android:id="@+id/button_payment"
        android:layout_row="9"
        android:layout_column="0"
        android:layout_columnSpan="3"
        android:text="결제하기"
        android:textSize="16dp"/>
</GridLayout>
```

또한, GridLayout 안에 컴포넌트들은 아래 표와 같이 구성되어있다. 총 10행 3열인 GridLayout이다. 

<img src="https://github.com/young43/BuyClothesApplication/blob/main/img/표.png" alt="GridLayout구성" width=350/>

### 2.3.2 BuyListViewItem 레이아웃

장바구니 화면과 마찬가지로 구매 화면에서도 구매할 상품의 리스트를 출력해야한다. 각 item 마다 필요한 정보들이 따로 존재하며, 수량을 표시하기위해 spinner를 정의하고자 하였다. 따라서 item에 대한 레이아웃을 따로 정의하였다.  GridLayout안에 TextView와 Spinner 컴포넌트로 구성하였다. 

구매 리스트 ltem 레이아웃은 _listview_item_buy.xml_ 파일에 구성되어있다.

```xml
<GridLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="15dp"
    android:rowCount="2"
    android:columnCount="3">

    <TextView
        android:layout_row="0"
        android:layout_column="0"
        android:id="@+id/textv_buy_product"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="상품명"
        android:textSize="20dp"/>

    <LinearLayout
        android:orientation="horizontal"
        android:layout_row="1"
        android:layout_column="0">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="수량: "
            android:textSize="17dp"/>

        <Spinner
            android:id="@+id/spinner_cnt"
            android:layout_width="100dp"
            android:layout_height="match_parent"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:text="개"
            android:textSize="17dp"/>
    </LinearLayout>

    <TextView
        android:layout_row="0"
        android:layout_column="1"
        android:layout_columnSpan="2"
        android:layout_rowSpan="2"
        android:gravity="right"
        android:id="@+id/textv_buy_price"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="가격"
        android:textSize="17dp"/>
</GridLayout>
```

### 2.3.3 Spinner 사용

구매 화면에서는 Spinner를 사용하여 수량을 다시 수정할 수 있도록 하였다.  Spinner의 경우 동적/정적으로 생성하는 방법이 존재하는데, 이 프로젝트에서는 정적생성 방식을 선택하였다. 따라서, _values/arrays.xml_ 파일에 데이터들을 생성하였다.

```xml
<string-array name="product_cnt">
    <item>1</item>
    <item>2</item>
    <item>3</item>
    <item>4</item>
    <item>5</item>
    <item>6</item>
    <item>7</item>
    <item>8</item>
    <item>9</item>
    <item>10</item>
</string-array>
```

<br/>

## 3. 구현내용

### 3.1 Activity

이 프로젝트에서는 액티비티가 단 1개가 사용되었다. BottomNavigationView에 있는 버튼 클릭시, 각 상황에 맞는 Fragment로 전환이 된다. 초기 Fragment는 상품선택화면(ProductFragment)로 설정하였다. 

BottomNavigationView의 _setOnNavigationItemSelectedListener_ 이벤트 리스너를 사용하여 어떤 버튼이 클릭되었는 지에 따라 setFrag 함수를 호출하도록 하였다. setFrag는 사용자정의 함수로, 각 case에 맞게 FragmentManager를 호출하여 화면전환을 실행하도록 한다. 파라미터가 null이 아닌 case는  _setArgument_ 를 호출하여 새 Fragment를 정의하고, Fragment 간의 값 전달을 위함이다. 

```java
bottomNavigationView = findViewById(R.id.bottomNavi);
bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_main:
                setFrag(0, null);
                break;
            case R.id.action_cart:
                setFrag(1, null);
                break;
            case R.id.action_purchase:
                setFrag(2, null);
                break;
        }
        return true;
    }
});
```

### 3.1.1 BottomNavigationView

BottomNavigationView는 하단에 있는 네비게이션 View이다. 각 버튼을 설정하기위해서  _menu/main_menu.xml_ 파일에 버튼의 id,텍스트,아이콘을 설정하였다.  앞에 있는 onNavigationItemSelected 이벤트 함수에서 _menuitem.getItemId_ 함수는 바로 이 item들의 id들을 반환하게 된다.

```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/action_main"
        android:enabled="true"
        android:icon="@drawable/ic_storefront_24px"
        android:title="상품화면"/>
    <item
        android:id="@+id/action_cart"
        android:enabled="true"
        android:icon="@drawable/ic_shopping_cart_24px"
        android:title="장바구니"/>
    <item
        android:id="@+id/action_purchase"
        android:enabled="true"
        android:icon="@drawable/ic_credit_card_24px"
        android:title="구매하기"/>
</menu>
```

### 3.2 Fragment

### 3.2.1 ProductFragment

첫번째 화면(상품 선택)에 대한 Fragment이다. 

- 이벤트 리스너 설정

  각 상품 Layout에 대한 _onClick_ 이벤트 리스너를 설정해 주었다. _onClick_ 이벤트 발생 시, 커스텀 다이얼로그를 띄우게 된다. 다이얼로그에는 구매하기/장바구니 버튼이 존재하고, X버튼도 존재한다. 

  ```java
  rootView = (ViewGroup) inflater.inflate(R.layout.fragment_product, container, false);
  
  layout1 = (RelativeLayout) rootView.findViewById(R.id.layout_product1);
  layout2 = (RelativeLayout) rootView.findViewById(R.id.layout_product2);
  layout3 = (RelativeLayout) rootView.findViewById(R.id.layout_product3);
  
  layout1.setOnClickListener(this);
  layout2.setOnClickListener(this);
  layout3.setOnClickListener(this);
  ```

- 버튼 클릭 이벤트

   커스텀 다이얼로그에서는 클릭했던 상품명과 가격을 출력하고, 구매/장바구니 버튼이 존재한다. 장바구니 버튼 클릭 시, 상품명과 가격에 대한 정보를 _Bundle_ 안에 저장한다. 구매버튼 클릭 시, 정보들을 Hashmap에 담고, 그것들을 _Bundle_ 안에 저장한다.  그리고 각 이동할 Fragment를 생성하고, _setArgument_ 함수 파라미터에 _Bundle_ 를 전달한다. 최종적으로 MainActivity의 setFrag 함수를 호출하여 화면을 전환한다. (커스텀 다이얼로그에 대한 설명은 3.3에 있습니다.)

  ```java
  public void onClick(View v) {
      String title = "";
      String price = "";
  
      switch (v.getId()) {
          case R.id.layout_product1:
              title = ((TextView) rootView.findViewById(R.id.textv_product_title1)).getText().toString();
              price = ((TextView) rootView.findViewById(R.id.textv_product_price1)).getText().toString();
              break;
  
          case R.id.layout_product2:
              title = ((TextView) rootView.findViewById(R.id.textv_product_title2)).getText().toString();
              price = ((TextView) rootView.findViewById(R.id.textv_product_price2)).getText().toString();
              break;
  
          case R.id.layout_product3:
              title = ((TextView) rootView.findViewById(R.id.textv_product_title3)).getText().toString();
              price = ((TextView) rootView.findViewById(R.id.textv_product_price3)).getText().toString();
              break;
  
          default:
              break;
  
      }
  
      CustomDialog customDialog = new CustomDialog(getActivity(), new CustomDialog.DialogListener() {
          @Override
          public void onPositiveClicked(int index, String title, String price) {
              MainActivity activity = (MainActivity) getActivity();
              if(index != 0){
                  Fragment fragment = null;
                  Bundle args = new Bundle();
                  if(index == 1){	// 장바구니
                      args.putString("title", title);
                      args.putString("price", price);
                      fragment = new CartFragment();
                      fragment.setArguments(args);
                  }else{	// 구매
                      ArrayList<HashMap<String, Object>> mapList = new ArrayList<>();
                      HashMap<String, Object> map = new HashMap<>();
                      map.put("title", title);
                      map.put("cnt", 1);
                      map.put("price", price);
                      mapList.add(map);
  
                      args.putSerializable("data", mapList);
                      fragment = new BuyFragment();
                      fragment.setArguments(args);
                  }
                  activity.setFrag(index, fragment);
              }
  
          }
  
          @Override
          public void onNegativeClicked() { }
      });
      customDialog.callFunction(title, price);	// 다이얼로그 호출
  }
  ```

### 3.2.2 CartFragment

두번째 화면(장바구니)에 대한 Fragment이다. 

- 인자값 받아오기

  _getArgument_ 함수를 호출하여 넘어온 파라미터가 있을 경우 전역변수 productList에 추가한다.

  ```java
  if(getArguments() != null){
      String product_title = getArguments().getString("title", "test");
      String product_price = getArguments().getString("price", "test");
      int imgSrc = getProductImagesrc(product_title);
  
      HashMap<String, String> map = new HashMap<>();
      map.put("title", product_title);
      map.put("price", product_price);
      map.put("img", imgSrc+"");
      productList.add(map);
  }
  ```

- Adapter 연결

  ListViewAdapter를 생성하고, ListView에 연결한다. 그리고 adapter에 각 아이템들을 추가한다. 추가한 item들은 ListView를 통해 볼 수 있다.

  ```java
  // Adapter 생성
  adapter = new ListViewAdapter(getActivity(), android.R.layout.simple_list_item_multiple_choice);
  
  // 리스트뷰 참조 및 Adapter달기
  listview = (ListView) rootView.findViewById(R.id.list_cart);
  listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
  listview.setAdapter(adapter);
  
  for(HashMap<String, String> m : productList){
      String title = m.get("title");
      String price = m.get("price");
      int src = Integer.parseInt(m.get("img"));
  
      adapter.addItem(ContextCompat.getDrawable(getActivity(), src), title, price);
  }
  ```

- 전체선택 구현

  전체선택 Checkbox 클릭할 경우, _onCheckedChanged_ 이벤트가 호출되어 adapter에 정의했던 checkAllTrue 함수가 호출된다. 각 item에 대한 checked값이 true로 설정되어 ListView 안에 있는 Checkbox들이 자동 클릭된다. 또한, 화면에 대한 업데이트를 위해 updateListView 사용자정의 함수를 사용하였다. 

  ```java
  // 전체선택
  CheckBox allCheck = (CheckBox) rootView.findViewById(R.id.checkbox_all);
  allCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
          if(isChecked)
              adapter.checkAllTrue();
          else
              adapter.checkAllFalse();
          updateListView();
      }
  });
  
  // 화면업데이트
  public void updateListView(){
      adapter.notifyDataSetChanged();
      listview.setAdapter(adapter);
      setListViewHeightBasedOnChildren(listview);	// 스크롤 뷰 높이 처리
  }
  ```

- 삭제기능 구현

  삭제버튼 클릭 시, 체크했던 아이템들 한해서 adapter에서 삭제된다. removeListItem 사용자정의 함수를 통해 dapter와 전역변수 productList에도 해당 아이템을 삭제한다. 또한, 화면을 업데이트 해준다.

  ```java
  Button deleteButton = (Button) rootView.findViewById(R.id.button_delete);
  deleteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          int count = adapter.getCount();
  
          for (int i = count-1; i >= 0; i--) {
              ListViewItem item = (ListViewItem) adapter.getItem(i);
              if(item.isChecked()){
                  removeListItem(i);
              }
          }
          updateListView();
      }
  });
  
  // 아이템 삭제
  public void removeListItem(int index){
      productList.remove(index);
      adapter.removeItem(index);
  }
  ```

- 구매버튼 이벤트

  구매버튼 클릭 시, 현재 check된 아이템들만 추출하여 수량을 계산한다. 장바구니 화면에서는 중복을 허용하고 있지만, 구매 화면에서는 총 수량으로 보여지게 된다.  따라서 중복값을 제거하고 각 상품의 갯수를 추출하여 Hashmap 형태로 데이터를 생성한다. BuyFragment에 넘길 때에는 _Bundle_의 _putSerializable_ 함수에 HashMap의 ArrayList 형태로 넘겨주게 된다. 만약 check된 아이템들이 없으면 Toast 메시지를 띄워주게 된다. 최종적으로는 MainActivity의 setFrag를 호출하여 화면전환이 이루어진다.

  ```java
  // 선택한 아이템이 없으면 구매버튼이 보이지 않는다.
  Button purchaseButton = (Button) rootView.findViewById(R.id.button_purchase);
  if(productList.size() == 0){
      purchaseButton.setVisibility(View.GONE);
  }
  
  purchaseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          ArrayList<HashMap<String, Object>> mapList = new ArrayList<>();
          HashMap<String, HashMap<String, Object>> map = new HashMap<>();
  
          int count = adapter.getCount();
          boolean flag_check = false;
  
          for (int i=0; i<count; i++) {
              ListViewItem item = (ListViewItem) adapter.getItem(i);
              if(item.isChecked()){
                  flag_check = true;
  
                  // 중복값을 제거하고, 각 상품의 갯수를 계산한다.
                  if(!map.containsKey(item.getTitle())){
                      HashMap<String, Object> tmp = new HashMap<>();
                      tmp.put("cnt", 1);
                      tmp.put("price", item.getDesc());
  
                      map.put(item.getTitle(), tmp);
  
                  }else{
                      HashMap<String, Object> tmp = map.get(item.getTitle());
                      int cnt = (Integer) tmp.get("cnt");
                      tmp.put("cnt", cnt+1);
  
                      map.put(item.getTitle(), tmp);
                  }
              }
          }
  
          // 선택한 상품이 없으면 예외처리
          if(!flag_check) {
              Toast.makeText(getActivity(), "선택한 상품이 없습니다.", Toast.LENGTH_LONG).show();
              return;
          }
  
          for (String key : map.keySet()) {
              HashMap<String, Object> mapData = new HashMap<>();
              mapData.put("title", key);
              mapData.put("cnt", map.get(key).get("cnt"));
              mapData.put("price", map.get(key).get("price"));
  
              mapList.add(mapData);
          }
  
          if(flag_check && map.size() > 0){
              Fragment fragment = new BuyFragment();
              Bundle args = new Bundle();
              args.putSerializable("data", mapList);
              fragment.setArguments(args);
  
              MainActivity activity = (MainActivity) getActivity();
              activity.setFrag(2, fragment);
          }
  
      }
  });
  ```

### 3.2.3 BuyFragment

세번째 화면(구매)에 대한 Fragment이다.  

- 인자값 받아오기

  데이터가 Hashmap 형태이므로, _getArguments().getSerializable_ 함수를 호출하여 데이터를 받아온다.  또한, 수량과 가격 정보를 통해 총 total 가격을 계산하고, 각 상품에 대한 map 데이터를 전역변수 buyList에 추가한다.

  ```java
  if(getArguments() != null){
      ArrayList<HashMap<String, Object>> data = (ArrayList<HashMap<String, Object>>) getArguments().getSerializable("data");
      for(HashMap<String, Object> map: data){
          String title = (String) map.get("title");
          int price = Integer.parseInt((String) map.get("price"));
          int cnt = (Integer) map.get("cnt");
          int totalPrice = price * cnt;
  
          HashMap<String, String> tmp = new HashMap<>();
          tmp.put("title", title);
          tmp.put("cnt", cnt+"");
          tmp.put("totalPrice", totalPrice+"");
          tmp.put("price", price+"");
  
          buyList.add(tmp);
      }
  }
  ```

- Adapter 연결(데이터 변화 감지)

  BuyListViewAdapter를 생성하고 ListView에 연결한다. 구매 화면의 ListView에는 Spinner로 수량을 설정할 수 있다. 따라서 수량이 변경될 때마다 상품에 대한 총 가격이 달라져야한다. _registerDataSetObserver_ 함수를 통해 adapter안의 데이터의 변화가 감지될 때마다 호출되어 상품의 총 가격과 전체 가격을 다시 계산하여 표시할 수 있도록 하였다. 

  ```java
  // Adapter 생성
  adapter = new BuyListViewAdapter(getActivity(), android.R.layout.simple_list_item_multiple_choice);
  
  // 수량에 따른 가격 변화
  adapter.registerDataSetObserver(new DataSetObserver() {
      @Override
      public void onChanged() {
          int total_price = 0;
          for(int i=0; i<adapter.getCount(); i++){
              BuyListViewItem item = (BuyListViewItem) adapter.getItem(i);
              total_price += Integer.parseInt(item.getDesc());
          }
          textTotalPrice.setText(total_price+"원");
          super.onChanged();
      }
  });
  
  // 리스트뷰 참조 및 Adapter연결
  listview = (ListView) rootView.findViewById(R.id.list_buy);
  listview.setAdapter(adapter);
  
  for(HashMap<String, String> m : buyList){
      String title = m.get("title");
      String cnt = m.get("cnt");
      String totalPrice = m.get("totalPrice");
      String price = m.get("price");
  
      // 첫 번째 아이템 추가.
      adapter.addItem(title, totalPrice, cnt, price);
  }
  ```

- 총 가격 계산

  adapter 데이터가 변하지 않았더래도, 화면에 총 가격을 표시해 주어야한다. 따라서 현재 adapter에 접근하여 가격 데이터를 가져오고, 총 가격을 계산하여 TextView에 표시하였다.

  ```java
  textTotalPrice = (TextView) rootView.findViewById(R.id.textv_total_price);
  
  int total_price = 0;
  for(int i=0; i<adapter.getCount(); i++){
      BuyListViewItem item = (BuyListViewItem) adapter.getItem(i);
      total_price += Integer.parseInt(item.getDesc());
  }
  textTotalPrice.setText(total_price+"원");
  ```

- 구매자 정보 가져오기

  주소지와 번호를 EditText 컴포넌트를 통해 받아오도록 하였으므로,  _findViewById_ 함수를 통해 객체를 받아오도록 하였다.

  ```java
  editAddress = (EditText) rootView.findViewById(R.id.edit_address);
  editNumber = (EditText) rootView.findViewById(R.id.edit_phoneNumber);
  ```

- 구매 기능 구현

  구매하기 버튼 클릭 시, 이벤트 리스너를 설정하고, 결제를 진행한다. 만약 결제할 상품이 아예 없거나, 구매자 정보를 입력하지 않았으면 Toast 메시지를 띄우고 예외처리를 하였다. 만약 결제가 성공하였다면, Toast 메시지를 띄우고 MainActivity setFrag 함수를 호출하고 화면을 전환한다. 

  ```java
  Button payButton = (Button) rootView.findViewById(R.id.button_payment);
  payButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          String address = editAddress.getText().toString();
          String number = editNumber.getText().toString();
          String totalPrice = textTotalPrice.getText().toString();
  
          if(totalPrice.equals("0원")){
              Toast.makeText(getActivity(), "결제할 상품이 없습니다.", Toast.LENGTH_LONG).show();
              return;
          }
  
          if(address.length()==0 || number.length()==0){
              Toast.makeText(getActivity(), "주소 및 번호를 입력하셔야합니다.", Toast.LENGTH_LONG).show();
              return;
          }
  
          // 결제가 성공하면 다시 첫화면으로 돌아감.
          MainActivity activity = (MainActivity) getActivity();
          activity.setFrag(0, null);
  
          Toast.makeText(getActivity(), "결제가 완료되었습니다.", Toast.LENGTH_LONG).show();
      }
  });
  ```

### 3.3 CustomDialog

구매 화면(ProductFragment)에서 상품을 클릭했을 때, 커스텀 다이얼로그가 생성된다.  다이얼로그에는 TextView(상품명, 가격), Button(구매하기, 장바구니, X버튼)이 존재한다. ProductFragment에서 callFunction 사용자정의 함수를 호출하면, 다이얼로그가 생성된다.

- 생성자

  ProductFragment 에서 다이얼로그를 생성할 때,  익명 리스너 함수를 받아오기위해 생성자를 선언하였다.

  ```java
  public CustomDialog(Context context, DialogListener myDialogListener) {
      this.context = context;
      this.myDialogListener = myDialogListener;
  }
  ```

- 리스너 interface 선언

  다이얼로그에서 어떤 버튼을 눌렀는 지에 대한 정보를 가져오기위해 필요한 interface를 CustomDialog 안에 선언하였다.

  ```java
  public interface DialogListener {
      void onPositiveClicked(int index, String title, String price);
      void onNegativeClicked();
  }
  ```

- 버튼 이벤트 설정

  구매/장바구니 버튼 클릭 시, onPositiveClicked 함수에 인자 값을 전달한다. 또한, _dissmiss_ 함수를 호출한다. X버튼 클릭 시, onNegativeClicked 함수와 _cancle_ 함수를 호출한다.

  ProductFragment 에서는 CustomDialog 를 생성할 때, onPositiveClicked 함수를 익명으로 재정의(Override)하여 사용하게 된다. 다이얼로그의 버튼이 클릭되면,  onClick 함수에서 onPositiveClicked 함수를 호출하게 되고, ProductFragment 에서 재정의한 onPositiveClicked가 다시 호출된다. (마치 이벤트함수 쓰는 것처럼 사용 가능함.) 

  ```java
  // 구매
  buttonPurchase.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          myDialogListener.onPositiveClicked(2, t_title, t_price);
          dlg.dismiss();
      }
  });
  
  // 장바구니
  buttonCart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          myDialogListener.onPositiveClicked(1, t_title, t_price);
          dlg.dismiss();
      }
  });
  
  // X버튼
  buttonCancle.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          myDialogListener.onNegativeClicked();
          dlg.cancel();
      }
  });
  ```

### 3.4 Custom ListView

### 3.4.1 ListViewItem(장바구니)

장바구니 화면의 ListView 안에 있는 Item에 대한 데이터 클래스이다. 상품 사진, 상품명, 가격, 체크여부가 존재한다.

```java
public class ListViewItem {
    private Drawable iconDrawable;	// 이미지
    private String titleStr;		// 상품명
    private String descStr;			// 가격
    private boolean checked;		// 체크여부

    public void setIcon(Drawable icon) {
        iconDrawable = icon;
    }
    public void setTitle(String title) {
        titleStr = title;
    }
    public void setDesc(String desc) {
        descStr = desc;
    }
    public void setChecked(boolean flag){ checked = flag; }

    public Drawable getIcon() {
        return this.iconDrawable;
    }
    public String getTitle() {
        return this.titleStr;
    }
    public String getDesc() {
        return this.descStr;
    }
    public boolean isChecked(){ return checked; }
}
```

### 3.4.2 ListViewAdapter(장바구니)

장바구니 화면에서 ListView를 사용하기위해 선언된 Adapter이며, ArrayAdapter를 상속한다. 각 item에 대한 핸들링은 adapter에서 진행한다.

- 화면 표시

  item 하나를 위한 레이아웃 _listview_item.xml_ 과 연결하고, 각 컴포넌트에 데이터들을 매칭시켜준다. 미리 선언된 전역변수 listViewItemList에 접근하여 item을 가져오게 된다.

  ```java
  if (convertView == null) {
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(R.layout.listview_item, parent, false);
  }
  
  // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
  ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
  TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
  TextView descTextView = (TextView) convertView.findViewById(R.id.textView2) ;
  CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox1);
  
  
  // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
  final ListViewItem listViewItem = listViewItemList.get(position);
  
  // 아이템 내 각 위젯에 데이터 반영
  iconImageView.setImageDrawable(listViewItem.getIcon());
  titleTextView.setText(listViewItem.getTitle());
  descTextView.setText(listViewItem.getDesc());
  checkBox.setChecked(listViewItem.isChecked());
  ```

- 아이템 추가

  새로운 아이템을 추가하는 함수이다. 해당 파라미터를 item에 매칭시키고, 전역변수 listViewItemList 에 추가한다.

  ```java
  public void addItem(Drawable icon, String title, String desc) {
      ListViewItem item = new ListViewItem();
  
      item.setIcon(icon);
      item.setTitle(title);
      item.setDesc(desc);
      item.setChecked(false);
  
      listViewItemList.add(item);
  }
  ```

- 전체선택

  장바구니 화면에서 전체 선택 기능 구현을 위한 것으로, 전역변수 listViewItemList를 순회하면서 모든 item의 checked 값을 하나로 통일한다.

  ```java
  public void checkAllTrue(){
      for(ListViewItem item : listViewItemList){
          item.setChecked(true);
      }
  }
  
  public void checkAllFalse(){
      for(ListViewItem item : listViewItemList){
          item.setChecked(false);
      }
  }
  ```

### 3.4.3 BuyListViewItem(구매)

구매 화면의 ListView 안에 있는 item에 대한 데이터 클래스이다.  상품명, 가격, 수량, 상품 총 가격으로 구성되어있다.

```java
public class BuyListViewItem {
    private String titleStr;
    private String descStr;
    private String cntStr;
    private String pricestr;

    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }
    public void setCntStr(String cnt) {
        cntStr = cnt ;
    }
    public void setPricestr(String price) {
        pricestr = price ;
    }

    public String getTitle() {
        return this.titleStr;
    }
    public String getDesc() {
        return this.descStr;
    }
    public String getCntStr() { return this.cntStr; }
    public String getPriceStr(){ return this.pricestr; }
}
```

### 3.4.4 BuyListViewAdapter(구매)

장바구니 adapter와 다른 점은 spinner 이벤트 구현이다. 각 item의 Spinner 값이 변경됐을 경우, 해당 상품의 총 가격을 수정해서 표시해야한다. 따라서, _setOnItemSelectedListener_ 리스너를 통해 _onItemSelected_ 함수를 재정의 하였다. 변경 감지는 그 이전 수량과 현재 선택된 수량이 다를 경우, 인지하게 된다.

```java
// Spinner(수량 설정)
Spinner cntSpinner = (Spinner) convertView.findViewById(R.id.spinner_cnt);
ArrayAdapter cntAdapter = ArrayAdapter.createFromResource(context, R.array.product_cnt, android.R.layout.simple_spinner_item);
cntAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
cntSpinner.setAdapter(cntAdapter);
cntSpinner.setSelection(Integer.parseInt(listViewItem.getCntStr())-1);

iCurrentSelection = cntSpinner.getSelectedItemPosition();
cntSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Spinner 값이 바뀔때마다 가격 변동 처리.
        // 변경감지는 그 이전 수량과 현재 선택된 수량이 다르면 인지.
        if(Integer.parseInt(listViewItem.getCntStr())-1 != position){
            int totalPrice = (position+1) * Integer.parseInt(listViewItem.getPriceStr());
            listViewItem.setCntStr((position+1)+"");
            listViewItem.setDesc(totalPrice+"");
            BuyListViewAdapter.super.notifyDataSetChanged();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
});
```