# Pwntools
**1**. **Useful function.** (Một số hàm này trong này **chỉ dùng** khi **trạng thái file** là: **not stripped**)
* **A.1.. ELF("./dir to file")**  
Muốn gọi hàm nào đó (**ví dụ fmtstr**) và bạn có thể tìm thấy địa chỉ của hàm đó bằng mã python này:<br>
```
    >>> e = ELF('./introduction')
    >>> print(hex(e.sym['fmtstr'])
```
 >**Creating an ELF object**<br>
elf = ELF('./vulnerable_program')<br>
***Getting a process*** <br>
p = elf.process() <br>
***The PLT and GOT***<br>
puts_plt = elf.plt['puts'] <br>
put_got = elf.got['puts'] <br>
*PLT : Procedure Linkage Table* <br>
*GOT : Global Offsets Table* <br>
**Functions** <br>
main_address = elf.functions['vuln']  # trả về địa chỉ hàm vuln <br>
main_address = elf.symbols['symbol']  #return address <br>
**elf.libc** <br>
libc = elf.libc <br>
elf.search(needle, writable = False) <br>
Example: libc.search(b'/bin/sh) or libc.symbols['puts']


* **A.2.. ELF("./dir to libc")**  
< Tương tự như **A.1** >
* **B. Context**:
```
context.arch = 'i386'
context.os = 'linux'
context.endian = 'little'
context.bits = 64

ex: context.binary = './vulnerable_binary'
    p = process()
 -> It will auto use the context binary.
```
**context.clear()** để nói cho pwntools biết nó đang tương tác với dạng file gì, hệ điều hành gì ấy mà<br>
Ví dụ: **`context.clear(os = 'linux', arch = 'i386', log_level = 'debug')`**<br>
* **C. Packing and Flat()**
```
p64(addr)
>>> p64(0x04030201) == b'\x01\x02\x03\x04'
>>> context.endian = 'big'
>>> p64(0x04030201) == b'\x04\x03\x02\x01'
u64(addr) 
>>> nguoc lai voi p64
flat(*args)
payload = flat(
    0x01020304,
    0x59549342,  >>> payload = p64(0x01020304) + p64(0x59549342) + p64(0x12186354)
    0x12186354
)
```
Ví dụ: **`flat( addr1, addr2, addr3,...)`** <br>
tương đương với: **`p64(addr1) + p64(addr2) + p64(addr3)`**
* **D**. **next()**

ví dụ: **`next(a, b, c)`**<br>
-> lần chạy thứ nhất output =` a`;<br>
-> lần chạy thứ hai output = `b`;<br>
-> lần chạy thứ ba output = `c`; <br>
* **E**. **GOT() và PLT()**

Global Offset Table and Procedure Linkage Table<br>
**Mục đích:** do các List Dynamic libraries Dependencies (được tham chiếu) tới các tệp nhị phân và cho ta thấy những thư viện libc và đường dẫn của chúng. Libc có thể được cập nhật mà ko ảnh hưởng đến tệp nhị phân. Suy ra địa chỉ của libc cũng có thể khác nhau giữa các phiên bản<br>
![image.png](https://images.viblo.asia/6ec78411-9d5e-4b43-8c38-55e9af9a40ae.png)

Vậy nếu chúng thay đổi như vậy mà ta muốn biết chính xác địa chỉ đó để call thì sinh ra 2 khái niệm là GOT và PLT.<br>
+GOT sẽ nhận nhiệm vụ chứa địa chỉ của libc được liên kết với tệp nhị phân<br>
+PLT sử dụng các hiệu số được lưu trữ trong GOT để quyết định vị trí cuối cùng thực tế của hàm

**2**. Finally, to communicate **interact** with a program, you can use these code snippets:
```html
>>> p = process('./introduction') #use this one to test locally
>>> p = remote('netcat.address', [port num]) #use this one to connect over netcat
>>> p.sendline('String to send')
>>> output_of_program = p.recv(256)
>>> output_line = p.recvline()
>>> output_until = p.recvuntil('input: ')
>>> p.interactive() #allow user to input to program directly
```
**3.** Bây giờ, để tổng hợp tất cả thông tin này lại với nhau, bạn sẽ cần phải viết một cái gì đó như thế này:
```python
>>> from pwn import *
>>> e = ELF('./introduction')
>>> p = process(e.path)
>>> p.recvuntil('later): ')
>>> canary = int(p.recvline(keepends = False), 16) #keepends = False bỏ ký tự xuống dòng
>>> p.sendline(b'a' * x + p64(canary) + b'a' * y + p64(e.sym['fmtstr'] + 1)) #x, y là ẩn số tùy vào chương trình.
```
Trong sự cố cụ thể này, hãy lưu ý rằng bạn cần thêm '+ 1' fmtstr adr, mà thông thường bạn sẽ không cần.<br>
Điều này là do các cuộc gọi scanf trong tương lai cần một giá trị rbp hợp lệ và điều này sẽ khắc phục nó một cách kỳ diệu.<br>
Ngoài ra, bạn có thể thấy các lệnh sau hữu ích, mặc dù không cần thiết cho việc khai thác:
```
>>> log.info('Func này ghi thông tin vào thiết bị đầu cuối của bạn, ví dụ canary: ' + hex(canary))
>>> gdb.attach(p) #open
* log.info(text)
>>>log.info('Pham Trung Kien')
[*] Pham Trung Kien
* log.success(text)
>>>log.success('ASLR bypassed!')
[*] ASLR bypassed
* log.error(text)
>>>log.error('The payload is too long')
[*] The payload ... long
```
một thiết bị đầu cuối với gdb chứa trạng thái hiện tại của chương trình của bạn
Bạn có thể đọc về nó tại [đây](https://docs.pwntools.com/en/stable/rop/rop.html)

------
# Cấu trúc máy tính (Computer Architecture)
|Phân đoạn bộ nhớ|
|:-:|
|**STACK** (1)|
|⬇️|
|*free memory* (2) |
|⬆️|
|**HEAP** (3)|
|Uninitialized data **.bss** (4)|
|Initialized data  **.data** (5)|
|.text (6)|<br>

### **Note**: <br>
*(1)* **STACK**: **"The set of values pushed for one function call is termed a “stack frame"**<br>
**Một frame** bao gồm ít nhất **1 địa chỉ return**,  Automatic variables are also allocated on the stack. 
**STACK** segment **liền kề** với **HEAP** segment, và chúng tiến về phía nhau. When stack pointer met the heap pointer => free memory **(2)** sẽ bị cạn kiệt (nhìn sơ đồ ở trên) <br>
*(2)* **Free memory**:  là nơi STACK và HEAP có thể phát triển <br>
*(3)* **HEAP**: Được quản lí bởi **malloc, realloc và free**, có thể sử dụng lệnh gọi hệ thống **brk** và **sbrk** để điều chỉnh kích thước của nó (lưu ý rằng việc sử dụng của **brk/ sbrk** và một "vùng heap" duy nhất không bắt buộc phải thực hiện malloc/realloc/free, Chúng cũng có thể được triển khai bằng cách sử dụng **mmap** để lưu trữ các vùng bộ nhớ ảo có khả năng không liền kề vào không gian địa chỉ ảo của quy trình). <br>
*(4)* **.bss segment**: bss stand for "**block started by symbol**".  Segment này được kernel khởi tạo thành số 0. <br>Ví dụ cho segment này:  `static int i;`<br>
*(5)* **.data segment**: chứa bất kì biến global hoặc biến static nào có giá trị **được xác định trước** và có thể sửa đổi. <br>Ví dụ cho segment này: 
```int debug_sesion = 1;
char string[] = "Hello World";

void foo (void)
{
    static int reset_cnt = 5; 
    ... 
   ```
 *Những giá trị này ban đầu được stored trong bộ nhớ read-only (Thông thường trong text). Sau đó mới được copy vào .data segment trong quá trình khởi động chương trình*<br>

   *(6)* **.text segment**: được biết đến như một phân đoạn code hoặc đơn giản chỉ là 1 text. Là một trong các phần của chương trình trong tệp đối tượng hoặc trong bộ nhớ chứa các lệnh thực thi.
## 1. Stack:
**a**.Trong **x86**, ngăn xếp chỉ đơn giản là 1 vùng trong ram được chọn làm **stack**. Thanh ghi **esp / rsp** giữ địa chỉ trong bộ nhớ nơi chứa **đáy** stack (**Còn rbp/ebp chứa địa chỉ trên cùng**). Khi một cái gì đó được push vào **stack**, esp nó sẽ **giảm đi** 4 (or rsp giảm 8 bit). Tương tự khi 1 lệnh **pop** được thực thi thì **rsp/esp** sẽ được truy xuất, sau đó rsp/esp **tăng lên** 8 or 4 bits. <br>
**b.** Stack được sử dụng cho 1 số mục đích:
* Lưu trữ các function arguments (ex: **scanf("%d", &arg1)**)
* Local variables
* Lưu trữ các tiến trình giữa các function calls

**Note instruction**: `mov    DWORD PTR [ebp-0x8],0x0`<br>
Nghĩa của câu lệnh này là move 0x0 vào địa chỉ **[ebp - 0x8]**, tiếp theo nó sẽ dành ra liên tiếp **3** địa chỉ tiếp theo (tính cả địa chỉ xuất phát [ebp - 0x8] đủ để lưu trữ **32** bit.
****
# Quy đổi đơn vị:
```
1 WORD = 2 bytes = 16 bits <br>
1 DWORD = 4 bytes = 32 bits <br>
1 QWORD = 8 bytes = 64 bits <br>
```
****
 # Checksec
   * RELPO: chế độ bảo vệ địa chỉ (Tức là khi nó **FULL RELPO** thì địa chỉ của nó cố định toàn phần, **partial**: cố định 1 phần, **disable**: địa chỉ chương trình không cố định, tương tác thông qua lệnh không thể dùng gdb) <br>
   * NX: Non-eXecute: enable: chống shellcode, disabler: có thể khai thác Shellcode có thể ktra bằng vmmap trong **peda**<br>
   * CANARY: chế độ bảo vệ stack <br>
   * PIE: <?>
# Calling Convention
Trong các tệp nhị phân Linux, thực sự chỉ có **hai** quy ước gọi thường được sử dụng: **cdecl** cho các tệp nhị phân **32** bit và **SysV** cho **64** bit
* **cdecl**: Các đối số được truyền trực tiếp vào stack theo thứ tự ngược lại
* **SysV**: Các đối số được truyền các thanh ghi nhất định:<br>

|RDI - Lưu trữ tham số thứ 1 để truyền vào hàm|
|:-:|
|**RSI - Lưu trữ tham số thứ 2 để truyền vào hàm**|
|**RDX - Lưu trữ tham số thứ 3 để truyền vào hàm**|
|**RCX - Lưu trữ tham số thứ 4 để truyền vào hàm**|
|**R8 - Lưu trữ tham số thứ 5 để truyền vào hàm**|
|**R9 - Lưu trữ tham số thứ 6 để truyền vào hàm**|

Sau đó các đối số còn sót lại sẽ được đẩy vào stack theo thứ tự ngược lại như trong cdecl



# Thư viện liên kết
```
Thư viện liên kết tĩnh có dạng libc.a thư viện liên kết động có định dạng: libc.so <br>
+ Liên kết tĩnh (ảnh 1):
     sử dụng thư viện tĩnh libc.a (một gói các tệp đối tượng) -> dùng để kết hợp thành 1 tệp nhị phân duy nhất
     => Khi nhiều đối tượng được liên kết -> kích thước file nhị phân có thể trở lên rất lớn
+ Liên kết động (ảnh 2):
    Sử dụng các thư viện được chia sẻ để tạo một tệp thực thi được liên kết động
    => Tệp nhị phân sẽ chỉ chứa mã thực thi và tên các thư viện được dùng chung, được nhúng trong tệp (không ảnh hưởng đến kích thước như liên kết tĩnh)
```
Ảnh 1:<br>
![image.png](https://images.viblo.asia/51688601-11a3-4c0d-b45c-96fc31c51033.png)

Ảnh 2:<br>
![image.png](https://images.viblo.asia/f55cffc4-2289-4e92-8446-ca0a67ae47ef.png)