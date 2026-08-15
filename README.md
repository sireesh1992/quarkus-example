# quarkus-example

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-example-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Hibernate ORM ([guide](https://quarkus.io/guides/hibernate-orm)): Define your persistent model with Hibernate ORM and Jakarta Persistence
- Hibernate Validator ([guide](https://quarkus.io/guides/validation)): Validate object properties (field, getter) and method parameters for your beans (REST, CDI, Jakarta Persistence)
- RESTEasy Classic JSON-B ([guide](https://quarkus.io/guides/rest-json)): JSON-B serialization support for RESTEasy Classic
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the active record or the repository pattern
- JDBC Driver - MySQL ([guide](https://quarkus.io/guides/datasource)): Connect to the MySQL database via JDBC

## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

[Related Hibernate with Panache section...](https://quarkus.io/guides/hibernate-orm-panache)


### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)



### code
import base64
import time
import tkinter as tk
from PIL import ImageTk
import qrcode

# 1. Read your 3MB text file
with open("output.txt", "r", encoding="utf-8") as f:
    data = f.read().strip()

CHUNK_SIZE = 2800  # High-density chunk size
DELAY_MS = 800     # Flashes less than 1 second per code (plenty of time for QR)

# 2. Slice text into indexed blocks
chunks = [data[i:i+CHUNK_SIZE] for i in range(0, len(data), CHUNK_SIZE)]
total_chunks = len(chunks)

# 3. Setup a clean Tkinter window interface
root = tk.Tk()
root.title("High-Speed Python Data Streamer")
root.geometry("700x750")
root.configure(bg="black")

label_img = tk.Label(root, bg="black")
label_img.pack(pady=10)

label_text = tk.Label(root, text="", fg="green", bg="black", font=("Arial", 16, "bold"))
label_text.pack()

current_index = 0

def show_next_qr():
    global current_index
    if current_index >= total_chunks:
        label_text.config(text="TRANSFER COMPLETE", fg="white")
        label_img.config(image="")
        return

    # Structure payload: index/total:data (e.g., "001/120:ZmFz...")
    prefix = f"{current_index:03d}/{total_chunks:03d}:"
    payload = prefix + chunks[current_index]

    # Generate the high-density QR asset natively
    qr = qrcode.QRCode(version=None, error_correction=qrcode.constants.ERROR_CORRECT_L, box_size=8, border=2)
    qr.add_data(payload)
    qr.make(fit=True)
    
    img = qr.make_image(fill_color="black", back_color="white")
    img_resized = img.resize((600, 600))
    
    # Render to screen
    photo = ImageTk.PhotoImage(img_resized)
    label_img.config(image=photo)
    label_img.image = photo
    
    label_text.config(text=f"Streaming: Block {current_index + 1} of {total_chunks}")
    current_index += 1
    
    root.after(DELAY_MS, show_next_qr)

# Launch stream
root.after(1000, show_next_qr)
root.mainloop()

