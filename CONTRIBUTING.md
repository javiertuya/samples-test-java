# Contributing to samples-test-java

## Pull requests

- Before creating a new pull request:
  - Ensure you have an updated version of the `main` branch. Only fast-forward merges are allowed.
  - Ensure that each PR will submit a single change with only one or a few significant commits, and the comment is appropriate. 
    Squash your local branch if needed.
  - Although the documentation is still written in Spanish, all PRs and commits should be written in english.
- Each pull request must pass the following checks before merge to ensure:
  - Tests have been added or updated when appropriate
  - All dynamic tests are passing (java)
  - All static tests are passing (sonarqube)
  - Additionally, javadoc documentation in methods and classes must be updated
    (note that javadoc is published for new releases only)

## Generating the reduced version (samples-test-dev)

Project [samples-test-dev](https://github.com/javiertuya/samples-test-dev) is a reduced version of this project.
If any change affects the features in the reduced version, it can be generated as indicated below:

- Ensure that `samples-test-dev` is checked out in a sibling folder of this project.
- Execute this script from the project folder: `./generate-samples-test-dev/generate-samples-test-dev.sh`
- Note: if running windows, you can use a git bash console.
- From `samples-test-dev` commit or PR the changes
 
  