# qtb - Quick Termbin

`qtb` è un semplice e leggero strumento a riga di comando per scaricare file e "paste" dal servizio [termbin.com](https://termbin.com) direttamente nel tuo terminale.

---

## Installazione

Per installare lo strumento, puoi clonare questa repository e compilare il sorgente, oppure scaricare l'eseguibile precompilato più recente dalla pagina **Releases** del tuo progetto.

Assicurati che l'eseguibile sia in una cartella inclusa nel `PATH` del tuo sistema per poterlo richiamare da qualsiasi posizione.

---

## Utilizzo

Il comando base richiede semplicemente l'ID del file da scaricare. L'output verrà stampato direttamente sullo standard output.

```bash
qtb <id> [-o <output_path>] [-b64] [-u]
```

### Opzioni disponibili

| Opzione            | Descrizione                                                     |
| ------------------ | --------------------------------------------------------------- |
| `-o <output_path>` | Salva l'output nel percorso specificato.                         |
| `-b64`             | Esegue la decodifica **Base64** del contenuto prima di salvarlo. |
| `-u`               | Decodifica il contenuto (Base64) e lo **decomprime** (unzip).   |
| `-h`               | Mostra il messaggio di aiuto.                                   |

---

## Esempi

**Scaricare un file e visualizzarlo nel terminale**
```bash
qtb paste_id
```

**Scaricare un file e salvarlo in una directory specifica**
```bash
qtb paste_id -o ./output/mio_file.txt
```

**Scaricare un file codificato in Base64 (es. un'immagine) e decodificarlo**
```bash
qtb immagine_id -o immagine_scaricata.png -b64
```

**Scaricare, decodificare e decomprimere un archivio .zip**
```bash
qtb archivio_id -o ./cartella_output/ -u
```

---

## Licenza


Questo progetto è rilasciato sotto la licenza MIT. Vedi il file [`LICENSE`]("https://github.com/HH-Tips/QuickTermBin/blob/main/LICENSE") per maggiori dettagli.
