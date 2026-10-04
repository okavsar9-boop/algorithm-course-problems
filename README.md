# Algorithm Problems

217 problems in 22 topics, each solved in Python, JavaScript and Java with tests.

- `python/`, `javascript/`, `java/`: one runnable file per problem (solution + tests), grouped by topic.
- `docs/`: the website (plain HTML/CSS/JS). Topics on the left, pick a problem, expand the solution, choose a language, copy code and tests.
- `NN. Topic Name/`: the source `.md` files the other folders are generated from.
- `tools/`: `build.py` regenerates everything from the source files, `validate.py` runs every file.

## View the website locally

1. Install Python 3 (skip if `python3 --version` already works):
   - macOS: `brew install python3`, or download it from https://www.python.org/downloads/
   - Windows: download from https://www.python.org/downloads/ and tick "Add Python to PATH"
2. From this folder run:
   ```bash
   cd docs
   python3 -m http.server 8000
   ```
3. Open http://localhost:8000

## Run a solution with its tests

No output means all tests passed.

```bash
python3 python/03_two_pointers/03_07_2sum.py     # needs Python 3.12+
node javascript/03_two_pointers/03_07_2sum.js     # needs Node.js
cd java/03_two_pointers && javac P03_07_2Sum.java && java P03_07_2Sum   # needs a JDK
```

## Regenerate after editing a source `.md`

```bash
python3 tools/build.py
python3 tools/validate.py
```

## Publish on GitHub Pages

1. Create a new repository on GitHub and push this folder to it.
2. Repository **Settings** > **Pages**.
3. Source: **Deploy from a branch**, branch `main`, folder `/docs`, then Save.
4. After a minute the site is live at `https://<your-username>.github.io/<repo-name>/`.
