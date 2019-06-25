# API testing with Postman


## Run tests with Newman (command line interface)

### Prerequisites
- Node.js

### Install Newman
```sh
npm install -g newman
```

### Run a collection
```sh
newman run polls-app.collection.json -e local.env.json -g polls-app.globals.json --folder signup
```

### Postman docs
https://learning.getpostman.com/docs/

### Newman docs
https://learning.getpostman.com/docs/postman/collection_runs/command_line_integration_with_newman
