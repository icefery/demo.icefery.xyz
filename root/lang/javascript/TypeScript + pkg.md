## TypeScript + pkg

### 配置

-   package.json

    ```json
    {
      "name": "my-app",
      "version": "0.0.1",
      "scripts": {
        "prettier": "prettier --write .",
        "dev": "ts-node ./src/main.ts",
        "clean": "rm -rf ./bin/* && rm -rf ./dist/*",
        "build": "tsc && pkg ."
      },
      "bin": "./dist/main.js",
      "pkg": {
        "output": "my-app",
        "outputPath": "./bin",
        "targets": ["node18-win-x64", "node18-linux-x64"]
      },
      "devDependencies": {
        "pkg": "^5.8.1",
        "ts-node": "^10.9.1",
        "typescript": "^5.3.2"
      }
    }
    ```

-   tsconfig.json

    ```json
    {
      "compilerOptions": {
        "target": "esnext",
        "module": "commonjs",
        "outDir": "./dist"
      }
    }
    ```
