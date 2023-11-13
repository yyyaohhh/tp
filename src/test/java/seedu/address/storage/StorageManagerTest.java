package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalModules.getTypicalModulePlan;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.UserPrefs;
import seedu.address.model.moduleplan.ModulePlan;
import seedu.address.model.moduleplan.ReadOnlyModulePlan;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonModulePlanStorage modulePlanStorage = new JsonModulePlanStorage(getTempFilePath("mp"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(modulePlanStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void modulePlanReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonModulePlanStorage} class.
         * More extensive testing of ModulePlan saving/reading is done in {@link JsonModulePlanStorageTest} class.
         */
        ModulePlan original = getTypicalModulePlan();
        storageManager.saveModulePlan(original);
        ReadOnlyModulePlan retrieved = storageManager.readModulePlan().get();
        assertEquals(original, new ModulePlan(retrieved));
    }

    @Test
    public void getModulePlanFilePath() {
        assertNotNull(storageManager.getModulePlanFilePath());
    }


}
