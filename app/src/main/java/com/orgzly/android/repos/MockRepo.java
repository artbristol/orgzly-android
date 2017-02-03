package com.orgzly.android.repos;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Wrapper around {@link com.orgzly.android.repos.LocalDbRepo}.
 *
 * Simulates slow network.
 *
 * TODO: Use LocalDirRepo instead, remove LocalDbRepo.
 */
public class MockRepo implements Repo {
    public static final String SCHEME = "mock";

    private static final long SLEEP_FOR_GET_BOOKS = 100;
    private static final long SLEEP_FOR_RETRIEVE_BOOK = 200;
    private static final long SLEEP_FOR_STORE_BOOK = 200;

    private LocalDbRepo localDbRepo;

    public MockRepo(Context context, String url) {
        localDbRepo = new LocalDbRepo(context, url);
    }

    @Override
    public boolean requiresConnection() {
        return false;
    }

    @Override
    public Uri getUri() {
        return localDbRepo.getUri();
    }

    @Override
    public List<VersionedRook> getBooks() throws IOException {
        try {
            Thread.sleep(SLEEP_FOR_GET_BOOKS);
        } catch (InterruptedException e) { }

        return localDbRepo.getBooks();
    }

    @Override
    public VersionedRook retrieveBook(Rook rook, File file) throws IOException {
        try {
            Thread.sleep(SLEEP_FOR_RETRIEVE_BOOK);
        } catch (InterruptedException e) { }

        return localDbRepo.retrieveBook(rook, file);
    }

    @Override
    public VersionedRook storeBook(File file, String path) throws IOException {
        try {
            Thread.sleep(SLEEP_FOR_STORE_BOOK);
        } catch (InterruptedException e) { }

        return localDbRepo.storeBook(file, path);
    }

    @Override
    public VersionedRook moveBook(Uri from, Uri to) throws IOException {
        try {
            Thread.sleep(SLEEP_FOR_STORE_BOOK);
        } catch (InterruptedException e) { }

        return localDbRepo.moveBook(from, to);
    }

    @Override
    public void delete(String url) throws IOException {

    }
}