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


// 1. Inject QR code library dynamically into the tab
const script = document.createElement('script');
script.src = 'https://cloudflare.com';
document.head.appendChild(script);

script.onload = () => {
  // 2. Build the Interface
  document.body.innerHTML = `
    <div id="setup" style="background:#1a1a1a; color:#fff; font-family:sans-serif; padding:40px; height:100vh; box-sizing:border-box; text-align:center;">
      <h2>Turbo QR Streamer (< 60 Seconds)</h2>
      <textarea id="txt" style="width:80%; height:250px; background:#2b2b2b; color:#fff; font-family:monospace; padding:10px; margin-bottom:20px;"></textarea>
      <br><button id="go" style="background:#22c55e; color:#fff; border:none; padding:15px 40px; font-size:18px; cursor:pointer; font-weight:bold; border-radius:4px;">START STREAM</button>
    </div>
    <div id="grid" style="display:none; background:#fff; height:100vh; width:100vw; box-sizing:border-box; grid-template-columns:repeat(2,1fr); grid-template-rows:repeat(2,1fr); gap:20px; padding:20px;"></div>
  `;

  document.getElementById('go').addEventListener('click', () => {
    const data = document.getElementById('txt').value.trim();
    if (!data) return alert("Paste your text first!");

    document.getElementById('setup').style.display = 'none';
    const grid = document.getElementById('grid');
    grid.style.style.display = 'grid';

    const chunkSize = 2300; // Optimal high-density capacity for Version 40 QR
    let index = 0;
    let frame = 0;

    function nextFrame() {
      if (index >= data.length) {
        grid.style.display = 'block';
        grid.innerHTML = '<h1 style="color:green; text-align:center; margin-top:40vh; font-family:sans-serif;">FINISHED</h1>';
        return;
      }

      grid.innerHTML = '';
      frame++;

      // Render 4 QR codes per frame side-by-side
      for (let q = 0; q < 4; q++) {
        if (index < data.length) {
          const container = document.createElement('div');
          container.style.cssText = 'display:flex; flex-direction:column; align-items:center; justify-content:center; border:1px solid #eee;';
          
          const qrDiv = document.createElement('div');
          container.appendChild(qrDiv);

          // Labels help the receiving script organize out-of-order frames
          const label = document.createElement('div');
          label.style.cssText = 'font-family:sans-serif; font-size:14px; font-weight:bold; margin-top:5px; color:#000;';
          const prefix = `F${String(frame).padStart(3,'0')}_Q${q}`;
          label.innerText = prefix;
          container.appendChild(label);
          
          grid.appendChild(container);

          const payload = `${prefix}:${data.substring(index, index + chunkSize)}`;
          
          // Generate the optical QR graphic
          new QRCode(qrDiv, {
            text: payload,
            width: window.innerHeight / 2.5,
            height: window.innerHeight / 2.5,
            correctLevel: QRCode.CorrectLevel.L // Low error correction maximizes data capacity
          });

          index += chunkSize;
        }
      }
      setTimeout(nextFrame, 1500); // 1.5 seconds gives the camera time to focus perfectly
    }
    nextFrame();
  });
};

