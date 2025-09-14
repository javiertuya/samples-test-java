# Contributing to samples-test-java

This project was created to be used in software engineering development and testing lab practices.  
Contributions that align with the scope and goals of the project are welcome.

Before contributing new features, please open an issue to discuss the proposed solution first.

## Pull Requests

- Before creating a new pull request:
  - Make sure your local `main` branch is up to date. Only fast-forward merges are allowed.
  - Each PR should introduce a single change with one or a few meaningful commits.  
    Ensure the commit message is clear and relevant. Squash your local branch if necessary.
  - Although the documentation is currently written in Spanish, issues, PRs, and commit messages must be written in English.

- Each pull request must pass the following checks before being merged:
  - Tests have been added or updated where appropriate.
  - All dynamic tests pass (Java).
  - All static analysis checks pass (SonarQube).
  - Javadoc documentation for methods and classes has been updated.  
    *(Note: Javadoc is published only for new releases.)*

## Generating the Reduced Version (samples-test-dev)

The [samples-test-dev](https://github.com/javiertuya/samples-test-dev) project is a simplified version of this repository.  
If any changes affect features in the reduced version, it can be regenerated as follows:

- Ensure that `samples-test-dev` is cloned in a sibling directory to this project.
- Run the following script from the root of this project:  
  `./generate-samples-test-dev/generate-samples  
- Note: if running windows, you can use a git bash console.
- From `samples-test-dev` commit or PR the changes
