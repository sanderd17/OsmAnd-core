package net.osmand.data;


public class IndexConstants {
	
	// Important : Every time you change schema of db upgrade version!!! 
	// If you want that new application support old index : put upgrade code in android app ResourceManager
	public final static int POI_TABLE_VERSION = 1;
	public final static int BINARY_MAP_VERSION = 2; // starts with 1
	public final static int VOICE_VERSION = 0; //supported download versions
	public final static int TTSVOICE_VERSION = 1; //supported download versions
	
	public static final String POI_INDEX_EXT = ".poi.odb"; //$NON-NLS-1$
	public static final String BINARY_MAP_INDEX_EXT = ".obf"; //$NON-NLS-1$
	
	public static final String GEN_LOG_EXT = ".gen.log"; //$NON-NLS-1$
	
	public static final String POI_INDEX_EXT_ZIP = ".poi.zip"; //$NON-NLS-1$
	public static final String VOICE_INDEX_EXT_ZIP = ".voice.zip"; //$NON-NLS-1$
	public static final String TTSVOICE_INDEX_EXT_ZIP = ".ttsvoice.zip"; //$NON-NLS-1$
	public static final String ANYVOICE_INDEX_EXT_ZIP = "voice.zip"; //$NON-NLS-1$ //to cactch both voices, .voice.zip and .ttsvoice.zip
	public static final String BINARY_MAP_INDEX_EXT_ZIP = ".obf.zip"; //$NON-NLS-1$
	
	public static final String EXTRA_ZIP_EXT = ".extra.zip";
	public static final String EXTRA_EXT = ".extra";
	
	public static final String RENDERER_INDEX_EXT = ".render.xml"; //$NON-NLS-1$
	
	public final static String POI_TABLE = "poi"; //$NON-NLS-1$
	
	public static final String INDEX_DOWNLOAD_DOMAIN = "download.osmand.net";
	public static final String APP_DIR = "osmand/"; //$NON-NLS-1$
	public static final String MAPS_PATH = "";
	public static final String BACKUP_INDEX_DIR= "backup/";
	public static final String GPX_INDEX_DIR= "tracks/";
	public static final String TILES_INDEX_DIR= "tiles/";
	public static final String SRTM_INDEX_DIR = "srtm/"; //$NON-NLS-1$
	public static final String AV_INDEX_DIR = "avnotes/"; //$NON-NLS-1$
	public static final String VOICE_INDEX_DIR = "voice/"; //$NON-NLS-1$
	public static final String RENDERERS_DIR = "rendering/"; //$NON-NLS-1$
	public static final String ROUTING_XML_FILE= "routing.xml";
	
	
	public static final String SQLITE_EXT = ".sqlitedb"; //$NON-NLS-1$
	public static final String TEMP_SOURCE_TO_LOAD = "temp";
	
	
}
