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
// 1. Set up the local offline interface
document.body.innerHTML = `
  <div id="setup" style="background:#111; color:#fff; font-family:sans-serif; padding:40px; height:100vh; box-sizing:border-box; text-align:center;">
    <h2>High-Velocity Offline Data Streamer</h2>
    <p>No internet required. Paste your Base64 text below and click Start.</p>
    <textarea id="txt" style="width:85%; height:300px; background:#222; color:#fff; font-family:monospace; padding:10px; font-size:14px; margin-bottom:20px; border:1px solid #444;"></textarea>
    <br>
    <button id="go" style="background:#007acc; color:#fff; border:none; padding:15px 40px; font-size:18px; cursor:pointer; border-radius:4px; font-weight:bold;">START HIGH-DENSITY STREAM</button>
  </div>
  <div id="grid" style="display:none; background:black; color:white; font-family:monospace; font-size:11px; margin:0; padding:10px; box-sizing:border-box; grid-template-columns:repeat(4,1fr); grid-template-rows:repeat(2,1fr); height:100vh; width:100vw; gap:15px; overflow:hidden;"></div>
`;

document.getElementById('go').addEventListener('click', () => {
  const data = document.getElementById('txt').value.trim();
  if (!data) return alert("Please paste your text first!");

  document.getElementById('setup').style.display = 'none';
  const grid = document.getElementById('grid');
  grid.style.display = 'grid';

  // We maximize the chunk size per cell to fit a standard 1080p/4K monitor
  const chunkSize = 5000; 
  const numCells = 8;    // 4x2 layout maximizes screen horizontal width
  const frameDuration = 2000; // 2 seconds gives your phone plenty of focus time

  let index = 0;
  let frame = 0;

  function nextFrame() {
    if (index >= data.length) {
      grid.style.display = 'block';
      grid.innerHTML = '<h1 style="color:#4caf50; text-align:center; margin-top:40vh; font-family:sans-serif; font-size:48px;">TRANSFER COMPLETE</h1>';
      return;
    }

    grid.innerHTML = '';
    frame++;

    for (let c = 0; c < numCells; c++) {
      if (index < data.length) {
        const cell = document.createElement('div');
        // Bright green borders and headers give the outside AI/OCR a clear structural anchor
        cell.style.cssText = 'border:2px solid #00ff00; padding:8px; word-break:break-all; overflow:hidden; font-size:10px; line-height:1.1; box-sizing:border-box; background:#050505; color:#ffffff;';
        
        const prefix = `[F${String(frame).padStart(3,'0')}_C${c}]`;
        const chunk = data.substring(index, index + chunkSize);
        
        // Wrap the index in a bright layout indicator
        cell.innerHTML = `<span style="color:#00ff00; font-weight:bold; background:#222; padding:2px 4px;">${prefix}</span><br>${chunk}`;
        
        grid.appendChild(cell);
        index += chunkSize;
      }
    }
    setTimeout(nextFrame, frameDuration);
  }

  nextFrame();
});
