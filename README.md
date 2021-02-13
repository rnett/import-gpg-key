# Import GPG Key

Action to import a GPG secret key, optionally exporting it to `~/.gnupg/secring.gpg`. Assumes Linux, `gpg` is the gpg
command, and that there are no other gpg keys. May work on non-Linux, but not guaranteed.

#### Inputs

* `secret-key` - the key to import. Should br produced with `gpg --export-secret-key --armor`. Should be stored in
  project secrets, **not passed in plaintext**.
* `export-secring` - `"true"` to export the key to `~/.gnupg/secring.gpg`. Optional, `false` by default.

#### Outputs

* `key-id` - the SHORT key id (the last 8 characters).