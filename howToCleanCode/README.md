## _BU YAZIDA JAVA İÇİN CLEAN CODE YAPILARINDAN BAHSEDİLECEK VE ÖRNEK BASİT BİR PROJE ÜZERİNDE UYGULANACAK_

### JAVA İÇİN İSİMLENDİRME:

1. metot isimleri camelCase
2. class isimleri PascalCase
3. değişken isimleri camelCase

Link: https://korhanozbek.com/yazilim/java-isimlendirme-kurallari/#:~:text=Proje%20%C4%B0simleri%20%3A,Geri%20kalan%20k%C4%B1sm%C4%B1%20k%C3%BC%C3%A7%C3%BCk%20yaz%C4%B1l%C4%B1r.

## _KİRLİ KOD ÖRNEKLERİ VEREREK CLEAN CODE'A EVRİLMEK VE SIRASIYLA REFACTOR İŞLEMLERİ._

### AŞAMA1:

Öncelikle Müşteri işlemlerimi yapabilmek için bir CustomerManager sınıfı oluşturuyorum. Bu CustomerManager sınıfının içerisine bir "add" metodu oluşturup, metodun içerisine müşterinin özelliklerini parametre olarak verip, eklenme işleminin çalıştığını burada belirtiyorum.

```
class CustomerManager{ 
	public void add(String firstName, String lastName,String identityNumber) { 
		System.out.println("eklendi"); 
	}		 		 	 
}
```

Daha sonra Main sınıfımda CustomerManager'ın instanceını oluşturup müşteri eklemeye çalışıyorum.

```
CustomerManager customerManager = new CustomerManager(); 
		customerManager.add("asdas", "asdasd", "asdasda"); 
		customerManager.add("asdas", "asdasd", "asdasda"); 
		customerManager.add("asdas", "asdasd", "asdasda"); 
		customerManager.add("asdas", "asdasd", "asdasda");
```

### _NEDEN HATALI BİR KOD?_

Buradaki(customerManager.add()) hata ise ileride müşterimin şehrini de eklemek istediğimde 
kod hata verecek.

Hepsini teker teker değiştirmem gerekir, ya da default bir şehir parametresi geçerek, kodu  veri açısından güvenilir olmayan bir hale getirebilirim.

### ****************** CLEAN CODE BÖLÜM 1/ENCAPSULATION ******************

Özellik tutan bir Customer sınıfı oluştururum. Bu oluşturduğum sınıfı ekleme metodumun içerisine parametre olarak geçerim.
Bir Customer classı eklenir ve CustomerManager içerisindeki add metoduna parametre olarak Customer geçilir.

```
class Customer{ 
	private String firstName; 
	private String lastName; 
	private String identityNumber; 
	// getters and setters 
}
```


```
public void add2(Customer customer) { 
            System.out.println("eklendiWithClass"); 		 
	}
```

Main sınıfımda bir Customer sınıfı oluşturup ekleme metodumun içerisine o Customer sınıfını yerleştiririm.
	
```
      Customer customer1 = new Customer(); 
		customer1.setFirstName("asd"); 
		customer1.setLastName("asda"); 
		customer1.setIdentityNumber("asda"); 
		customerManager.add2(customer1);
```
	  
### ****************** CLEAN CODE BÖLÜM 2/TECHNICAL DEBT ******************

 Bazen sistemlerde katman işlerine girmeden acil değişikliğe ihtiyaç duyulur. Bu işlemi yaparken 
 ileride refactor edilebilecek bir şekilde yapmamız gerekir. Bu "Technical Debt" olarak
 adlandırılır.

 İsim soyisim varlığını kontrol eden bir if bloğu yazıyoruz ve eğer geçerliyse ekleme işlemini ekliyoruz.

add metodunun içerisine eklenen blok: 	

```
if(!(customer.getFirstName().isEmpty() && customer. 
		getLastName().isEmpty() && customer.getIdentityNumber().isEmpty())) { 
	System.out.println("eklendiWithClass"); 
}
```

Bu kod ile ekleme işlemim iç içe, birbirine bağlı oldu. İleride refactor edebilmem için bunu ilişkilendirmemem gerekiyor.

 Bunun için boş değilse ekle şeklinde kural koymak  yerine boşsa bir hata fırlat şeklinde değiştiriyorum.

```
public void add2(Customer customer) throws Exception { 	 
		if(customer.getFirstName().isEmpty() || customer. 
				getLastName().isEmpty() || customer.getIdentityNumber().isEmpty()) { 
			throw new Exception("VALİDASYON HATASI"); 
		} 
		System.out.println("eklendiWithClass"); 	 
	}
```

### ****************** REFACTORING TECHNICAL DEBT ******************

Bu validasyon sistemini ben başka yerlerde de çağırmak isteyebilirim. Kullandığım başka yerler varsa mevcut validasyon sisteminde bir değişiklik yapmak isteyebilirim ve hepsiyle tek tek uğraşmam gerekebilir. Bu gibi durumların önüne geçmek için bu validasyonu CustomerManager içerisinde bir metot haline getiriyorum ve add metodunun içerisinde validate metodunu çağırıyorum.

```
	public void add2(Customer customer) throws Exception { 
		validate(customer); 
		System.out.println("eklendiWithClass"); 
	} 
	private void validate(Customer customer) throws Exception { 
		if (customer.getFirstName().isEmpty() || customer.getLastName().isEmpty() 
				|| customer.getIdentityNumber().isEmpty()) { 
			throw new Exception("VALİDASYON HATASI"); 
		} 
	}
```

### ****************** REFACTORING TECHNICAL DEBT2 ******************

Mevcut kurallarımız ile 3 tane customer eklediğimizi varsayalım. 
Yeni bir kural ile birlikte Customer sınıfının firstName özelliğinin minimum 2 karakter olması gerekli. Ancak bu kural sadece eklenen 2. müşteri için geçerli olmalı. Öncelikle ekleme işlemleri veritabanı işlemler olduğu için CustomerRepository (veya CustomerDao) adı altında bir sınıf oluşturuyorum ve sadece ekleme işlemini bunun içerisinde tanımlıyorum. Manager'ım içerisindeki ekleme işlemini ise CustomerRepository'den getiriyorum. 

Orijinal add metodumun içerisinde oynama yapmak yerine farklı isterler için o anlık bir add metodu oluşturuyorum. İsim,  soyisim ve kimlik doğrulamasını sadece validate içerisinde yapmıştım, burada hepsini ayırıp ihtiyacım olanları kullanıyorum. 

```
public void add(Customer customer) throws Exception { 
		validateFirstName(customer); 
		validateIdentityNumber(customer); 
		validateLastName(customer); 
		CustomerRepository customerRepository = new CustomerRepository(); 
		customerRepository.add(customer); 
	} 
	public void addByOtherBusiness(Customer customer) throws Exception { 
		validateFirstName(customer); 
		validateIdentityNumber(customer); 
		validateLastName(customer); 
		CustomerRepository customerRepository = new CustomerRepository(); 
		customerRepository.add(customer); 
	} 
	private void validateFirstName(Customer customer) throws Exception { 
		if (customer.getFirstName().isEmpty()) { 
			throw new Exception("VALİDASYON HATASI"); 
		} 
	} 
	private void validateIdentityNumber(Customer customer) throws Exception { 
		if (customer.getIdentityNumber().isEmpty()) { 
			throw new Exception("VALİDASYON HATASI"); 
		} 
	} 
	private void validateLastName(Customer customer) throws Exception { 
		if (customer.getLastName().isEmpty()) { 
			throw new Exception("VALİDASYON HATASI"); 
		} 
	}
```

Şimdi ise FirstName uzunluğunu kontrol edecek bir metot ekliyorum ve kullanmak istediğim addByOtherBusiness metodunun içerisine ekliyorum. İlerisi için kullanmak istediğim yerlerde bu metotla ekleme yapacağım.

```
private void validateFirstNameLength(Customer customer) throws Exception { 
		if (customer.getFirstName().length()<2) { 
			throw new Exception("VALİDASYON HATASI"); 
		}
```

### _NEDEN VALIDATELERI AYIRDIK?_

İleride yeni bir kuralla birlikte identityNumber validate'ine ihtiyaç olmadığı söylenildi. Bu şekilde kolaylıkla sistemden kaldırılabilir. 

NOT: DRY(DONT REPEAT YOURSELF) Kendini tekrar etme ki, sisteme bir değişiklik geldiği zaman kendini tekrar ettiğin her yeri değiştirmek zorunda kalma.

### ****************** CLEAN CODE BÖLÜM 3/İŞ KURALI YAZMAK ******************

Benim iş katmanımda yazılı olan validasyonlar dışında iş kurallarım da olmalı. Örneğin ekleyeceğim müşteri mevcut mu, kontrol et, ona göre ekleme işlemini yap. Ayrıca iş katmanımda yazdığım kuralların şiir gibi alt alta okunur bir şekilde olması gerekiyor.

Bunun için; öncelikle veritabanımda(CustomerRepository) müşteri var mı kontrolümü gerçekleştiriyorum. 

```
	public boolean customerExists(Customer customer) { 
		return true; 
	}
```

Şimdilik müşteri mevcutmuş gibi davransın istediğim için return true; şeklinde döndürüyorum.

İş metotlarımın içerisine teker teker if customerExists şeklinde yazarak yine kendimi tekrar edeceğim. Bunun yerine CustomerManager içerisinde müşteri mevcut mu kontrol etsin diye bir metot oluşturuyorum.

```
	private void checkCustomerExists(Customer customer) throws Exception { 
		CustomerRepository customerRepository = new CustomerRepository(); 
		if(customerRepository.customerExists(customer)) { 
			throw new Exception("Müşteri mevcut"); 
		} 
	}
```

CustomerManager sıfınının ekleme metoduna bunu ekliyorum.

```
	public void add(Customer customer) throws Exception { 
		validateFirstName(customer); 
		validateIdentityNumber(customer); 
		validateLastName(customer); 
		CustomerRepository customerRepository = new CustomerRepository(); 
		checkCustomerExists(customer); 
		customerRepository.add(customer); 
	}
```

Okunaklı, kendini tekrar etmeyen bir iş sınıfı.

ÖNEMLİ NOT : Validasyonla, iş kodu birbirinden farklıdır. Validasyonlarımızda veritabanına dahi gitmedik.

### ****************** CLEAN CODE BÖLÜM 3.1/VALİDATİON YAZMAK ******************

Her validasyon kuralım için bir validasyon metodu yazmam gerekmiyor. Şu anki projemiz sade bir java projesi olduğu için bu projeye ekleme yapmayacağım. Ancak bir maven projesi olsaydı yani paket yönetimi yapabildiğim bir proje olsaydı aşağıdaki şekilde bir bağımlılık ekleyerek validasyon yönetimi yapabilirdim.

```
<dependency> 
    <groupId>org.springframework.boot</groupId> 
    <artifactId>spring-boot-starter-validation</artifactId> 
</dependency>
```

Peki bunu nasıl yapacağım? Notasyon kullanarak. Bir nesnemi oluşturmak isterken nasıl kurallar istiyorsam o kurallara uygun notasyonlar ekliyorum. Örnek olarak: 

```
public class CreateCarRequest { 
	@Size(min=2,max=20) 
	private String description; 
	@Min(value = 100) 
	private double dailyPrice;
}
```

Bu projeyi baz aldığımız zaman örneğin anotasyonlarla ekleme yaptım ancak gün geldi yabancılara da hizmet vermeye başladığımda kimlik numarasının zorunluluğunun kalkması gerekiyor. Bunun için notasyonlarımı hiçbir zaman entity üzerine belirtmiyorum. SOLID'e aykırı bir durum oluyor zaten. Bunun için farklı yöntemler mevcut. Örneğin request-response pattern uygulayarak createCustomerRequest içerisinde notasyonlarımı belirtebilirim.


### ****************** CLEAN CODE BÖLÜM 4/DEĞER VE REFERANS TİPİ ANLAMAK ******************

STACK AND HEAP (KAYNAK: https://www.gokhan-gokalp.com/stack-heap-kavramlari/)

Stack ve Heap kavramlarından kısaca bahsetmek gerekirse, ram’in mantıksal bölümleridir diyebiliriz. Stack’de değer tipleri, pointer ve adresler saklanırken, Heap’de ise referans değerleri saklanmaktadır.

Struct tipindeki değişkenler değer tipleridir ve Stack içerisinde saklanmaktadır. Class tipindeki değişkenler ise referans tipleridir ve referansları Stack’de kendisi ise Heap’de saklanır.

![alt text](https://github.com/Mertcali/Etiya_Camp/blob/main/workshop6HowToCleanCode/image.png)

Örneğin yukarıdaki şekilde görüldüğü üzere Int değer tipinde olduğu için direk Stack üzerinde “000001” adresine yerleştirilmiştir. String ise referans tipinden bir değişken olduğu için Stack üzerinde “00000H” adresinde ve “FFF0GH” olarak referans adresi tutulmuştur.


Stack & Heap’in genel özelliklerine kısaca bir bakacak olursak;

- Stack ve Heap ram’in mantıksal bölümleridir.
- Stack LIFO mantığında çalışır. Yani son gelen ilk olarak çıkar.
- Stack’de değer tipleri, pointer ve adresler saklanırken Heap’de ise referans değerleri saklanır.
- Stack daha hızlıdır. Ulaşılmak istenen veriler ard arda sıralanmış olur.
- Heap ortak olarak kullanılır ve uygulama başlatıldığında başlar.


--> ÇIKTILARI NE OLMALI
```
		int sayi1 = 10; 
		int sayi2 = 20; 
		sayi1 = sayi2; 
		sayi2=100; 
		System.out.println(sayi1); 
		int[] sayilar1 = {1,2,3,4,5}; 
		int[] sayilar2 = {10,20,30,40,50}; 
		sayilar1 = sayilar2; 
		sayilar1[0] = 100; 
		System.out.println(sayilar1[0]);
```

### ****************** CLEAN CODE BÖLÜM 4.1/CLEAN CODE İÇİN REFERANS TİPİ ******************

Sistemime çalışanları da dahil etmek istiyorum ve onların da oluşturduğum Customer sınıfı gibi Ad, Soyad, Kimlik özellikleri olduğunu düşünelim. Customer ve Employee sınıfı da benim sistemimin kullanıcıları ancak ben kullanıcı oluşturmak veya kullanıcıları getirmek istediğim zaman ayrı ayrı getirmek zorundayım. Bunları ortak alanda tutabileceğim bir User sınıfı oluşturuyorum. Bu sayede User'ı newlediğim zaman ister Customer ister Employee oluşturabilir, sadece User aracılığıyla hepsini getirebilirim.

```
class Customer extends User{ 
	private String customerNumber; 	
        //getters and setters
} 
class Employee extends User{ 
	private String employeeNumber; 
	 //getters and setters
} 
class User { 
	private String firstName; 
	private String lastName; 
	private String identityNumber;
        //getters and setters
}
```

Ayrıca sınıfların kendine ait özelliklerini kendi içlerinde belirtiyorum.

Sonuç olarak;

```
		User user = new Customer(); 
		User user2 = new Employee();
```

şeklinde bir yazımda hata çıkmıyor.

### ****************** CLEAN CODE BÖLÜM 4.1/ÇIPLAK CLASS KALMASIN ve IF SUISTIMALINI ENGELLEMEK ******************

Oluşturduğumuz class'ların çıplak olmaması bizim için önemli. Çıplaklıktan kasıt bir extend veya bir implementi olmalı. Çünkü bu bizim için ileride bağımlılık sıkıntıları oluşturacak. 

--> Peki nasıl? 

Örneğin ben veritabanına erişim için CustomerRepository kullanıyorum ve bu veritabanı erişimi çeşitli olabilir, Oracle, PostgreSQL gibi. Bunun kontrolünü içeride iflerle bir veritabanı tipi göndererek sağlıyorum. Bu da if suistimaline yol açıyor. Ayrıca gün gelicek bir veritabanı çeşidini çıkarmak veya başka bir tip eklemek isteyebilirim, tekrardan eklemeler ve çıkarmalar yapmak zorunda kalacağım iflerimin içine.

--> Peki nasıl yazılmalıydı?

CustomerRepository'min hizmet aldığı bir Interface'e ihtiyacı vardır. Neden bir interface? Çünkü içeride kullandığım operasyonlar aynıdır, kodları farklıdır.
Bunun için bir CustomerRepositoryService interface'i oluşturuyorum.

```
interface CustomerRepositoryService{ 
	void add(Customer customer); 
	boolean customerExists(Customer customer); 
}
```

Daha sonra her çeşidim için bir class oluşturup CustomerRepositoryService'imi implement ediyorum.

```
class EfCustomerRepository implements CustomerRepositoryService{ 
	@Override 
	public void add(Customer customer) { 
		System.out.println("Entity Framework ile veritabanına eklendi"); 
	} 
	@Override 
	public boolean customerExists(Customer customer) { 
		return true; 
	}
} 
class NhCustomerRepository implements CustomerRepositoryService{ 
@Override 
	public void add(Customer customer) { 
		System.out.println("NH ile veritabanına eklendi"); 
	} 
	@Override 
	public boolean customerExists(Customer customer) { 
		// TODO Auto-generated method stub 
		return true; 
	}
}
```

Başka bir zaman bir veritabanı çeşidi eklemek istediğim zaman yapmam gereken tek şey artık bir class oluşturup implement etmek.

Artık sade CustomerRepository'imi silebilirim. Ancak Manager'da nasıl kullanıcam neye göre belli edeceğim veritabanını? Bunun için manager'a gelip oluşturduğumuz interface'i getiriyoruz. Bunu dependency injection denilen bir yöntemle yapıyoruz.

```
	CustomerRepositoryService customerRepositoryService; 
	public CustomerManager(CustomerRepositoryService customerRepositoryService) { 
		this.customerRepositoryService = customerRepositoryService; 
	}
```

Artık içeride CustomerRepository newleyerek ona bağımlı olmak yerine oluşturduğum servisten çağırıyorum metodumu.

```
customerRepositoryService.add(customer);
```

Artık main tarafında CustomerManager oluştururken kullanmak istediğim çeşide göre bir oluşturma yapıyorum ve böylelikle sistemime dinamiklik getiriyorum.

```
CustomerManager customerManager = new CustomerManager(new EfCustomerRepository());
```

### ****************** CLEAN CODE BÖLÜM 5/ADAPTASYON YÖNTEMİ ******************

Bu sistemde kullanıcıların sistemimizde varlığını kontrol ediyoruz ancak gerçek hayatta varlığını kontrol etmiyoruz. Bu yüzden çok fazla sahte hesap oluşabilir. Sistemimize kimlik doğrulama getirmeye çalışalım.

--> Böyle bir durumda kodu nereye yazacağız?  Dış entegrasyon, mikroservis gibi iş ile alakalı olan durumları business katmanında düşünmeliyiz.

Direkt olarak Mernis Web Servis'i eklersem sistemime sistem mernis'e bağımlı oluyor. Manager'ım bağımlı olmamalı.

Örnek olarak dokunamayacağımız bir KpsService yazalım.

```
class KpsService{ 
	public boolean checkPerson(long tcNo, String adi, String soyadi, int dogumYili) { 
		return true; 
	} 
}
```

Bu sisteme dokunamayacağımız için bunu sistemimize adapte etmemiz lazım. Repository mantığında sistemime adapte etmek için bir interface'e ihtiyacım var. 

User sınıfımın adını isimlendirme uygun olsun diye Person'a çevirdim.
```
interface CheckPersonService{ 	 
	boolean CheckPerson(Person Person); 	 
}
```

```
	CustomerRepositoryService customerRepositoryService; 
	CheckPersonService checkPersonService; 
	public CustomerManager(CustomerRepositoryService customerRepositoryService,CheckPersonService checkPersonService) { 
		this.customerRepositoryService = customerRepositoryService; 
		this.checkPersonService = checkPersonService; 
	}
```

ile manager sınıfımın içerisinde servisimi tanıtıyorum.

```
	private void checkPersonExists(Person person) throws Exception { 
		if(!(checkPersonService.CheckPerson(person))) { 
			throw new Exception("Kişi bilgileri hatalı"); 
		} 
	}
```

Şeklinde bir metot yazıyorum. Metot isimleri karışabilir. Burada kişinin gerçek bir insan olup olmadığını kontrol edeceğim.

```
checkPersonExists(customer);
```

Şeklinde Manager 'ın içerisine ekledim. 

Artık mainde manager oluşturulurken bende bir CheckPersonService isteyecek. Ancak Kps'yi belirtmediğim için oraya yazamıyorum. 

--> Neden implement edip direkt oraya yazamıyorum? 

`Çünkü ben O KODA DOKUNAMIYORUM.`


Bu yüzden adapter design kullanacağız.
```
class KpsServiceAdapter implements CheckPersonService{ 
	@Override 
	public boolean CheckPerson(Person person) { 
		KpsService kpsService = new KpsService(); 
		return kpsService.checkPerson(Long.parseLong(person.getIdentityNumber()), person.getFirstName(), person.getLastName(), person.getDogumYili()); 
	} 
}
```

Kps için bir adapter yazıyorum. Bu sefer içeride KpsService çağırabilirim, nasılsa onun adapteri bir sıkıntı çıkarmayacak. Mernis sistemini bu şekilde kendime adapte ettim. Manager'da çağıramıyordum Mernis sistemini ancak onu adapte ettiğim kendi sistemimi çağırabilirim.

```
CustomerManager customerManager = new CustomerManager(new EfCustomerRepository(),new KpsServiceAdapter());
```

### ****************** CLEAN CODE BÖLÜM 5/COMMENTLER ******************

Fazla commentten kaçınılmalı. Kod kalabalığı gibi gözüküp, bir karmaşıklık yaratıyor. Metot içerisine comment yazmaktan kaçınılmalı.

### ****************** CLEAN CODE BÖLÜM 5.1/SUMMARY KULLANIMI ******************
Add metodunun içerisine birden fazla metot eklemek karışıklığa ve okunurlukta çirkinliğe sebep olacaktır. Bunun yerine; örneğin checkPersonExists() ile KPS doğrulaması yapıyordum, onun orijinal haline giderek onun üzerine Summary bırakabilirim. 

Summary Nasıl bırakılır?

> `"/**" tırnak içindeki karakterleri yapıp enter'a bastıktan sonra summary oluşturur.`

### ****************** CLEAN CODE BÖLÜM 6/KATMANLI MİMARİYE REFACTORING ******************

En baştan itibaren düşündüğümüzde aslında sınıflandırarak gitseydik işlemlerimizi hem okunur, hem değiştirilebilir, hem de bütün işlemlerin daha kolaylaşacağı bir sistem olurdu. Temel anlamda katmanlarımız ve katmanlarımız soyut, somut klasörleri bulunmakta. 

business.abstracts klasörüne CheckPersonService, .concretes'e CustomerManager'ı ekledik.
Entities katmanımızda .abstracts klasörüne Entity diye bir interface oluşturup bütün .concretes'teki varlıklarıma bunu ekleyip, hepsinin bir Entity olduğunu belli etmek için kullanabilirdim. Ancak ileride bunu anotasyonlarla halledicez. Şu an için entities.concretes klasöründe Person,Customer,Employee oluşturuldu.

dataAccess katmanında .abstract klasörlemesinde CustomerRepositoryService, .concretes'te ise entityFramework ve nHibernate klasörlemesi yapıldı. Bu klasörlemelerin içerisinde kendi classları yazıldı.

Sistemimize adapte ettiğimiz, dışarıdan eklediğimizi varsaydığımız Kps sistemi için bir adapters sınıfı oluşturulup içerisinde serviceAdapters klasörlemesine gidildi ve adapte etme işlemi burada gerçekleştirildi.

Bu şekilde projemizi katmanlarına ayırdık.

### ****************** CLEAN CODE BÖLÜM 7/IoC ÇEVİK KODLAMA ******************

Spring IoC yapılandırması buraya eklenmedi. Ancak IoC ile manager'da oluşturduğumuz dependency injection işlemleri rahatlıyor diyebiliriz şu an için.

--> Spring IoC nedir?

Link: https://gokhana.medium.com/inversion-of-control-ioc-nedir-ve-avantajlar%C4%B1-nelerdir-cf05e42c16e4

IoC dependency injection yapıldığında nesnelerin life cycle'ını yöneten kutuya deniyor.Bellekte somut instancelar bu kutunun içinde duruyor. Spring IoC dependency injection yapıldığında örneğin @autowired ile o kutudan alınıyor.

(C# için Autofac,Ninject akıllara gelebilir.)

### ****************** CLEAN CODE BÖLÜM 8/Yeni Bir Senaryoyla Pekiştirmek ******************
Senaryo:

- Bir eğitim satış sisteminde eğitim listesindeki fiyatlandırma bölümünü yazmak istiyoruz.

- Kurslar fiyatlarıyla listelenecektir.

- Belirli dönemlerde kampanyalar yapılıyor. Kampanya dahilinde fiyatlar kampanyaya göre gösteriliyor.

- Kampanya standart bir fiyat olabileceği gibi, yüzdelik indirim de olabilmektedir.

- Bu proje farklı bir proje olarak oluşturulmuştur. howToCleanCode2 projesine bakabilirsiniz.








